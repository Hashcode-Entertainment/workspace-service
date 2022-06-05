package com.workspaceservice.git;

import com.workspaceservice.exceptions.FileSystemException;

import java.net.URL;
import java.nio.file.Path;

import static com.workspaceservice.git.GitUtils.generateRepoUrl;


public class GitServer {
    private final Path rootPath;
    private final URL baseUrl;

    public GitServer(Path rootPath, URL baseUrl) {
        this.rootPath = rootPath;
        this.baseUrl = baseUrl;
    }

    public URL createRepo(String id) throws FileSystemException {
        JGit.createRepo(resolveRepoPath(id));

        return generateRepoUrl(baseUrl, id);
    }

    @SuppressWarnings("RedundantThrows")
    public void deleteRepo(String id) throws FileSystemException {
        JGit.deleteRepo(rootPath.resolve(id));
    }

    public CommitBuilder createCommitBuilder(String repoId) {
        return new CommitBuilder(resolveRepoPath(repoId));
    }

    private Path resolveRepoPath(String repoId) {
        return rootPath.resolve(repoId + ".git");
    }
}
