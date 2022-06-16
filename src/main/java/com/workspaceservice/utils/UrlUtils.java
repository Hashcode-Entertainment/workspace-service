package com.workspaceservice.utils;

import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public abstract class UrlUtils {
    public static URL resolveUrl(@NotNull URL base, @NotNull String path) {
        try {
            return base.toURI().resolve(path).toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new AssertionError(e);
        }
    }

    public static URL url(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
