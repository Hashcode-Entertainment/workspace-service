package com.workspaceservice.git;

import com.workspaceservice.exceptions.FileSystemException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;

import static com.workspaceservice.git.JGit.resolveBranchRef;
import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;
import static org.eclipse.jgit.lib.FileMode.REGULAR_FILE;

public class CommitBuilder {
    private final Path repoPath;
    private Repository repo;
    private final DirCache dirCache = DirCache.newInCore();
    private boolean committed = false;

    public CommitBuilder(Path repoPath) {
        this.repoPath = repoPath;
    }

    private void openRepo() throws FileSystemException {
        try {
            repo = new FileRepository(repoPath.toFile());
        } catch (IOException e) {
            throw new FileSystemException(e);
        }
    }

    public void addFile(Path path, byte[] bytes) throws FileSystemException {
        openRepo();
        var entry = new DirCacheEntry(path.toString());
        entry.setLength(bytes.length);
        entry.setLastModified(Instant.now());
        entry.setFileMode(REGULAR_FILE);

        try (var inserter = repo.getObjectDatabase().newInserter()) {
            var blobId = inserter.insert(OBJ_BLOB, bytes);
            entry.setObjectId(blobId);
        } catch (IOException e) {
            throw new FileSystemException(e);
        }

        var dirCacheBuilder = dirCache.builder();
        dirCacheBuilder.add(entry);
        dirCacheBuilder.finish();
    }

    public void commit(String message, String branch, String authorName, String authorEmail)
            throws FileSystemException {

        try {
            commitInternal(message, branch, authorName, authorEmail);
        } catch (IOException e) {
            throw new FileSystemException(e);
        } finally {
            repo.close();
        }
    }

    private void commitInternal(String message, String branch, String authorName, String authorEmail)
            throws IOException {

        if (committed) {
            throw new IllegalStateException("Already committed");
        }

        var branchRefName = resolveBranchRef(branch);

        ObjectId commitId;
        try (var inserter = repo.getObjectDatabase().newInserter()) {
            var treeId = dirCache.writeTree(inserter);
            var commitBuilder = new org.eclipse.jgit.lib.CommitBuilder();
            commitBuilder.setTreeId(treeId);
            commitBuilder.setMessage(message);

            var author = new PersonIdent(authorName, authorEmail);
            commitBuilder.setAuthor(author);
            commitBuilder.setCommitter(author);

            var branchRef = repo.exactRef(branchRefName);
            if (branchRef != null) {
                commitBuilder.setParentId(branchRef.getObjectId());
            }

            commitId = inserter.insert(commitBuilder);
        }

        var updateRequest = repo.updateRef(branchRefName);
        updateRequest.setNewObjectId(commitId);
        var result = updateRequest.update();
        if (result != RefUpdate.Result.FAST_FORWARD && result != RefUpdate.Result.NEW) {
            throw new IllegalStateException("Failed to update branch, result: " + result);
        }

        committed = true;
    }
}
