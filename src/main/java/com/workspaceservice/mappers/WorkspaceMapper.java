package com.workspaceservice.mappers;

import com.workspaceservice.Workspace;
import com.workspaceservice.dao.WorkspaceDAO;
import com.workspaceservice.dto.WorkspaceDTO;

import static com.workspaceservice.utils.UrlUtils.url;

public abstract class WorkspaceMapper {
    public static Workspace toWorkspace(WorkspaceDAO workspaceDAO) {
        if (workspaceDAO == null) {
            return null;
        }
        return new Workspace(
                workspaceDAO.getId(),
                workspaceDAO.getOwner(),
                workspaceDAO.getTemplate(),
                url(workspaceDAO.getUrl())
        );
    }

    public static WorkspaceDAO toWorkspaceDao(Workspace workspace) {
        if (workspace == null) {
            return null;
        }
        return new WorkspaceDAO(
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
                workspace.owner(),
                workspace.template() != null ? workspace.template().toString() : null,
                workspace.url().toString()
        );
    }
}
