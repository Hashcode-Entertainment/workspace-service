package com.workspaceservice.git;

import org.jetbrains.annotations.NotNull;

public abstract class GitUtils {
    public static String generateRepoPath(@NotNull String id) {
        return String.format("/%s.git", id);
    }

    public static String resolveBranchRef(@NotNull String branch) {
        return "refs/heads/" + branch;
    }
}
