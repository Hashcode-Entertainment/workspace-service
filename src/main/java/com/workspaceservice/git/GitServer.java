package com.workspaceservice.git;

import com.workspaceservice.exceptions.FileSystemException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

import static com.workspaceservice.git.GitUtils.generateRepoPath;

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
}
