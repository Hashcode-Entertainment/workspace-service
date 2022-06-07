package com.workspaceservice.git;

import com.workspaceservice.exceptions.FileSystemException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.lib.Repository;

import java.nio.file.Path;

import static com.workspaceservice.git.JGit.addFileToDirCache;
import static com.workspaceservice.git.JGit.loadRepo;

public class CommitBuilder {
    private final Path repoPath;
    private Repository repo = null;
    private final DirCache dirCache = DirCache.newInCore();
    private final DirCacheBuilder dirCacheBuilder = dirCache.builder();
    private boolean committed = false;

    public CommitBuilder(Path repoPath) {
        this.repoPath = repoPath;
    }

    private void openRepo() throws FileSystemException {
        if (repo != null) {
            return;
        }

        repo = loadRepo(repoPath);
    }

    public void addFile(Path path, byte[] bytes) throws FileSystemException {
        openRepo();
        addFileToDirCache(dirCacheBuilder, path, bytes, repo);
    }

    public void addFile(Path path, String content) throws FileSystemException {
        addFile(path, content.getBytes());
    }

    public void commit(String message, String branch, String authorName, String authorEmail)
            throws FileSystemException {

        openRepo();

        if (committed) {
            throw new IllegalStateException("Already committed");
        }

        JGit.commit(repo, dirCache, dirCacheBuilder, message, branch, authorName, authorEmail);
        repo.close();
        committed = true;
    }
}
