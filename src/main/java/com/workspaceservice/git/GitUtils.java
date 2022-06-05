package com.workspaceservice.git;

import java.net.MalformedURLException;
import java.net.URL;

public abstract class GitUtils {
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
