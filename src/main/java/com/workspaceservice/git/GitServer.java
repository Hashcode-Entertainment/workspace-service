package com.workspaceservice.git;

import com.workspaceservice.exceptions.FileSystemException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

import static com.workspaceservice.git.GitUtils.generateRepoPath;

@RequiredArgsConstructor
public class GitServer {
    private final Path rootPath;

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

    private Path resolveRepoPath(String repoId) {
        return rootPath.resolve(repoId + ".git");
    }
}
