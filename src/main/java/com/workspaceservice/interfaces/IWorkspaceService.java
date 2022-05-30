package com.workspaceservice.interfaces;

import com.workspaceservice.dao.Workspace;
import com.workspaceservice.dto.WorkspaceDTO;

public interface IWorkspaceService {
    Workspace createWorkspace (WorkspaceDTO workspaceDTO);
}
