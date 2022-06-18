package com.workspaceservice.mappers;

import com.workspaceservice.domain.Workspace;
import com.workspaceservice.dao.WorkspaceDAO;
import com.workspaceservice.dto.WorkspaceDTO;

import static com.workspaceservice.utils.UrlUtils.url;

public abstract class WorkspaceMapper {
    public static Workspace toWorkspace(WorkspaceDAO workspaceDAO) {
        if (workspaceDAO == null) {
            return null;
        }

        var updateHookDAO = workspaceDAO.getUpdateHook();
        var updateHook = UpdateHookMapper.toUpdateHook(updateHookDAO);

        return new Workspace(
                workspaceDAO.getId(),
                workspaceDAO.getOwner(),
                workspaceDAO.getTemplate(),
                url(workspaceDAO.getUrl()),
                updateHook
        );
    }

    public static WorkspaceDAO toWorkspaceDao(Workspace workspace) {
        if (workspace == null) {
            return null;
        }

        var updateHook = workspace.updateHook();
        var updateHookDao = UpdateHookMapper.toUpdateHookDao(updateHook);

        return new WorkspaceDAO(
                workspace.id(),
                workspace.owner(),
                workspace.template(),
                workspace.url().toString(),
                updateHookDao
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
