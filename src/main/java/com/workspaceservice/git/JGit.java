package com.workspaceservice.git;

import com.workspaceservice.exceptions.FileSystemException;
import kotlin.io.FilesKt;
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

import static com.workspaceservice.git.GitUtils.resolveBranchRef;
import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;
import static org.eclipse.jgit.lib.FileMode.REGULAR_FILE;

public abstract class JGit {
    public static Repository loadRepo(Path path) throws FileSystemException {
        try {
            return new FileRepository(path.toFile());
        } catch (IOException e) {
            throw new FileSystemException(e);
        }
    }

    public static void createRepo(Path path) throws FileSystemException {
        try (var repo = loadRepo(path)) {
            boolean bare = true;
            repo.create(bare);
        } catch (IOException e) {
            throw new FileSystemException(e);
        }
    }

    public static void deleteRepo(Path path) {
        FilesKt.deleteRecursively(path.toFile());
    }

    public static void addFileToDirCache(DirCache dirCache, Path path, byte[] bytes, Repository repo)
            throws FileSystemException {

        var entry = new DirCacheEntry(path.toString());
        entry.setLength(bytes.length);
        entry.setLastModified(Instant.now());
        entry.setFileMode(REGULAR_FILE);

        var blobId = insertBlob(repo, bytes);
        entry.setObjectId(blobId);

        var dirCacheBuilder = dirCache.builder();
        dirCacheBuilder.add(entry);
        dirCacheBuilder.finish();
    }

    public static void commit(
            Repository repo,
            DirCache dirCache,
            String message,
            String branch,
            String authorName,
            String authorEmail
    ) throws FileSystemException {

        try {
            commitThrowingIOException(repo, dirCache, message, branch, authorName, authorEmail);
        } catch (IOException e) {
            throw new FileSystemException(e);
        }
    }

    private static void commitThrowingIOException(
            Repository repo,
            DirCache dirCache,
            String message,
            String branch,
            String authorName,
            String authorEmail
    ) throws IOException {

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

        updateOrCreateRef(repo, branchRefName, commitId);
    }

    private static void updateOrCreateRef(Repository repo, String refName, ObjectId objectId)
            throws IOException {

        var updateRequest = repo.updateRef(refName);
        updateRequest.setNewObjectId(objectId);
        var result = updateRequest.update();
        if (result != RefUpdate.Result.FAST_FORWARD && result != RefUpdate.Result.NEW) {
            throw new IllegalStateException("Failed to update ref, result: " + result);
        }
    }

    private static ObjectId insertBlob(Repository repo, byte[] bytes) throws FileSystemException {
        try (var inserter = repo.getObjectDatabase().newInserter()) {
            return inserter.insert(OBJ_BLOB, bytes);
        } catch (IOException e) {
            throw new FileSystemException(e);
        }
    }
}
