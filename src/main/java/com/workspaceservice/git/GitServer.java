package com.workspaceservice.git;

import com.workspaceservice.exceptions.FileSystemException;
import org.eclipse.jgit.http.server.GitServlet;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.ReceiveCommand;
import org.eclipse.jgit.transport.ReceivePack;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.workspaceservice.git.GitUtils.generateRepoPath;
import static com.workspaceservice.git.JGit.resolveRepo;

@Component
public class GitServer {
    private final Path rootPath;

    @Autowired
    public GitServer(@Qualifier("gitServerRoot") Path rootPath) {
        this.rootPath = rootPath;
    }

    public String createRepo(@NotNull String id) throws FileSystemException {
        JGit.createRepo(resolveRepoPath(id));

        return generateRepoPath(id);
    }

    @SuppressWarnings("RedundantThrows")
    public void deleteRepo(@NotNull String id) throws FileSystemException {
        JGit.deleteRepo(rootPath.resolve(id));
    }

    public CommitBuilder createCommitBuilder(@NotNull String repoId) {
        return new CommitBuilder(resolveRepoPath(repoId));
    }

    public String forkRepo(@NotNull String originalId, String copyId) throws FileSystemException {
        JGit.forkRepo(resolveRepoPath(originalId), resolveRepoPath(copyId));
        return generateRepoPath(copyId);
    }

    private Path resolveRepoPath(String repoId) {
        return rootPath.resolve(repoId + ".git");
    }

    public Servlet getServlet() {
        var gitServlet = new GitServlet();
        gitServlet.setRepositoryResolver((req, name) -> resolveRepo(name, rootPath));
        gitServlet.setReceivePackFactory(this::createReceivePack);

        return gitServlet;
    }

    private ReceivePack createReceivePack(HttpServletRequest httpServletRequest, Repository repository) {
        var pack = new ReceivePack(repository);
        pack.setPostReceiveHook(this::postReceiveHook);
        return pack;
    }

    private void postReceiveHook(ReceivePack receivePack, Collection<ReceiveCommand> receiveCommands) {
        var updatedRefs =
                receiveCommands
                        .stream()
                        .map(ReceiveCommand::getRefName)
                        .toList();

        var repo = receivePack.getRepository();
        var repoId = repo.getDirectory().getName().split("\\.")[0];

        postReceiveHooks.forEach(it -> it.run(updatedRefs, repoId));
    }

    private final List<PostReceiveHook> postReceiveHooks = new ArrayList<>();


    public void onPostReceive(PostReceiveHook postReceiveHook) {
        postReceiveHooks.add(postReceiveHook);
    }

    public String getFileContent(String repoId, Path path, String branch) throws FileSystemException {
        return JGit.getFileContent(resolveRepoPath(repoId), path, branch);
    }
}
