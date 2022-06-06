package com.workspaceservice;

import com.workspaceservice.exceptions.FileSystemException;
import com.workspaceservice.git.GitServer;
import com.workspaceservice.user.User;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.UUID;

import static com.workspaceservice.utils.UrlUtils.resolveUrl;


@RequiredArgsConstructor
public class WorkspaceManager {
    private final GitServer gitServer;
    private final URL gitServerUrl;

    public Workspace createWorkspace(@NotNull User owner, @Nullable Workspace template)
            throws FileSystemException {

        var id = UUID.randomUUID();
        var repoPath = gitServer.createRepo(id.toString());
        var repoUrl = resolveUrl(gitServerUrl, repoPath);
        var templateId = template != null ? template.id() : null;
        return new Workspace(id, owner, templateId, repoUrl);
    }

    public void deleteWorkspace(@NotNull UUID id) throws FileSystemException {
        gitServer.deleteRepo(id.toString());
    }
}
