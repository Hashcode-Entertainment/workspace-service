package com.workspaceservice.mappers;

import com.workspaceservice.Workspace;
import com.workspaceservice.dao.WorkspaceEntity;
import com.workspaceservice.dto.WorkspaceDTO;

import static com.workspaceservice.utils.UrlUtils.url;

public abstract class WorkspaceMapper {
    public static Workspace toWorkspace(WorkspaceEntity workspaceEntity) {
        if (workspaceEntity == null) {
            return null;
        }
        return new Workspace(
                workspaceEntity.getId(),
                workspaceEntity.getOwner(),
                workspaceEntity.getTemplate(),
                url(workspaceEntity.getUrl())
        );
    }

    public static WorkspaceEntity toWorkspaceEntity(Workspace workspace) {
        if (workspace == null) {
            return null;
        }
        return new WorkspaceEntity(
                workspace.id(),
                workspace.owner(),
                workspace.template(),
                workspace.url().toString()
        );
    }

    public static WorkspaceDTO toWorkspaceDTO(Workspace workspace) {
        if (workspace == null) {
            return null;
        }
        return new WorkspaceDTO(
                workspace.id(),
                workspace.owner().toString(),
                workspace.template() != null ? workspace.template().toString() : null,
                workspace.url().toString()
        );
    }
}
