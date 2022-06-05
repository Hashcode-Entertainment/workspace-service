package com.workspaceservice.git;

import kotlin.io.FilesKt;
import org.eclipse.jgit.internal.storage.file.FileRepository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;

public abstract class JGit {
    public static void createRepo(Path path) throws IOException {
        try (var repo = new FileRepository(path.toFile())) {
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

    public static String resolveBranchRef(String branch) {
        return "refs/heads/" + branch;
    }
}
