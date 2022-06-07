package com.workspaceservice;

import com.workspaceservice.user.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.UUID;

public record Workspace(
        @NotNull UUID id,
        @NotNull String owner,
        @Nullable UUID template,
        @NotNull URL url
) {
}
