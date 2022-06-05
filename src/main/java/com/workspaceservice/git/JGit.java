package com.workspaceservice.git;

import kotlin.io.FilesKt;
import org.eclipse.jgit.internal.storage.file.FileRepository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;

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

    public static URL generateRepoUrl(URL baseUrl, String id) {
        try {
            return new URL(baseUrl, id + ".git");
        } catch (MalformedURLException e) {
            throw new AssertionError(e);
        }
    }
}
