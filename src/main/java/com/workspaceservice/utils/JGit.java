package com.workspaceservice.utils;

import kotlin.io.FilesKt;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.internal.storage.file.FileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

public abstract class JGit {
    public static void createRepo(String name, Path parentDirectory) throws IOException {
        var repoPath = parentDirectory.resolve(name);
        try (var repo = new FileRepository(repoPath.toFile())) {
            boolean bare = true;
            repo.create(bare);
        }
    }

    public static void deleteRepo(Path path) {
        FilesKt.deleteRecursively(path.toFile());
    }

    public static void commitFiles(
            Path path,
            Map<String, String> files,
            String commitMessage,
            String authorName,
            String authorEmail,
            Path tempDirectory
    ) throws IOException, GitAPIException {
        var randomFolderName = UUID.randomUUID().toString();
        var workingDirectory = tempDirectory.resolve(randomFolderName);
        var cloneCommand = Git
                .cloneRepository()
                .setURI(path.toAbsolutePath().toString())
                .setDirectory(workingDirectory.toFile());

        try (var git = cloneCommand.call()) {
            for (var file : files.entrySet()) {
                var filePath = workingDirectory.resolve(file.getKey());
                Files.writeString(filePath, file.getValue());
            }

            git.add().addFilepattern(".").call();
            git.commit().setMessage(commitMessage).setAuthor(authorName, authorEmail).call();
            git.push().call();
        } catch (InvalidRemoteException e) {
            throw new IllegalArgumentException("Provided path doesn't point to a git repository", e);
        } catch (TransportException e) {
            throw new AssertionError(e);
        }

        FilesKt.deleteRecursively(workingDirectory.toFile());
    }
}
