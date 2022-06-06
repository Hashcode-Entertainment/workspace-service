package com.workspaceservice.utils;

import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;

public abstract class UrlUtils {
    public static URL resolveUrl(@NotNull URL base, @NotNull String path) {
        try {
            return new URL(base, path);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
