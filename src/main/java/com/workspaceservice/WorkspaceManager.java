package com.workspaceservice;

import com.workspaceservice.exceptions.FileSystemException;
import com.workspaceservice.git.GitServer;
import com.workspaceservice.mappers.WorkspaceMapper;
import com.workspaceservice.repositories.WorkspaceRepository;
import com.workspaceservice.user.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.UUID;

import static com.workspaceservice.utils.UrlUtils.resolveUrl;


@Component
public class WorkspaceManager {
    private final GitServer gitServer;
    private final URL gitServerUrl;
    private final WorkspaceRepository workspaceRepository;

    @Autowired
    public WorkspaceManager(GitServer gitServer, @Qualifier("gitServer") URL gitServerUrl, WorkspaceRepository workspaceRepository) {
        this.gitServer = gitServer;
        this.gitServerUrl = gitServerUrl;
        this.workspaceRepository = workspaceRepository;
    }

    public Workspace createWorkspace(@NotNull User owner, @Nullable Workspace template)
            throws FileSystemException {

        var id = UUID.randomUUID();
        var repoPath = gitServer.createRepo(id.toString());
        var repoUrl = resolveUrl(gitServerUrl, repoPath);
        var templateId = template != null ? template.id() : null;

        var workspace = new Workspace(id, owner, templateId, repoUrl);
        var workspaceEntity = WorkspaceMapper.toWorkspaceEntity(workspace);
        workspaceRepository.save(workspaceEntity);

        return workspace;
    }

    public Workspace getWorkspace(UUID id) {
        return WorkspaceMapper.toWorkspace(workspaceRepository.findById(id));
    }

    public void deleteWorkspace(@NotNull UUID id) throws FileSystemException {
        gitServer.deleteRepo(id.toString());
    }
}
