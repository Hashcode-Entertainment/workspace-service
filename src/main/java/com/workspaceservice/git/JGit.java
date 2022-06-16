package com.workspaceservice.git;

import com.workspaceservice.exceptions.FileSystemException;
import kotlin.io.FilesKt;
import lombok.SneakyThrows;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.NoSuchElementException;

import static com.workspaceservice.git.GitUtils.resolveBranchRef;
import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;
import static org.eclipse.jgit.lib.FileMode.REGULAR_FILE;
import static org.springframework.util.FileSystemUtils.copyRecursively;

public abstract class JGit {
    public static Repository loadRepo(@NotNull Path path) throws FileSystemException {
        try {
            return new FileRepository(path.toFile());
        } catch (IOException e) {
            throw new FileSystemException(e);
        }
    }

    public static void createRepo(@NotNull Path path) throws FileSystemException {
        try (var repo = loadRepo(path)) {
            boolean bare = true;
            repo.create(bare);
        } catch (IOException e) {
            throw new FileSystemException(e);
        }
    }

    public static void deleteRepo(@NotNull Path path) {
        FilesKt.deleteRecursively(path.toFile());
    }

    public static void addFileToDirCache(
            @NotNull DirCacheBuilder dirCacheBuilder,
            @NotNull Path path,
            @NotNull byte[] bytes,
            @NotNull Repository repo
    ) throws FileSystemException {

        var entry = new DirCacheEntry(path.normalize().toString().replace("\\", "/"));
        entry.setLength(bytes.length);
        entry.setLastModified(Instant.now());
        entry.setFileMode(REGULAR_FILE);

        var blobId = insertBlob(repo, bytes);
        entry.setObjectId(blobId);

        dirCacheBuilder.add(entry);
    }

    public static void commit(
            @NotNull Repository repo,
            @NotNull DirCache dirCache,
            @NotNull DirCacheBuilder dirCacheBuilder,
            @NotNull String message,
            @NotNull String branch,
            @NotNull String authorName,
            @NotNull String authorEmail
    ) throws FileSystemException {

        try {
            commitThrowingIOException(repo, dirCache, dirCacheBuilder, message, branch, authorName, authorEmail);
        } catch (IOException e) {
            throw new FileSystemException(e);
        }
    }

    private static void commitThrowingIOException(
            Repository repo,
            DirCache dirCache,
            DirCacheBuilder dirCacheBuilder,
            String message,
            String branch,
            String authorName,
            String authorEmail
    ) throws IOException {

        var branchRefName = resolveBranchRef(branch);

        dirCacheBuilder.finish();

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

    @SneakyThrows(FileSystemException.class)
    public static Repository resolveRepo(String name, Path reposDirectory) {
        var path = reposDirectory.resolve(name);
        if (!Files.exists(path)) {
            throw new NoSuchElementException("Repository not found");
        }

        return loadRepo(reposDirectory.resolve(name));
    }

    public static void forkRepo(Path original, Path copy) throws FileSystemException {
        try {
            copyRecursively(original, copy);
        } catch (IOException e) {
            throw new FileSystemException(e);
        }
    }
}
