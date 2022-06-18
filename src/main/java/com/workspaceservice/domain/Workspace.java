package com.workspaceservice.domain;

import com.workspaceservice.domain.UpdateHook;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.UUID;

public record Workspace(
        @NotNull UUID id,
        @NotNull String owner,
        @Nullable UUID template,
        @NotNull URL url,
        @Nullable UpdateHook updateHook
) {
}
