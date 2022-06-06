package com.workspaceservice.interfaces;

import com.workspaceservice.dao.WorkspaceEntity;
import com.workspaceservice.dto.WorkspaceDTO;

public interface IWorkspaceService {
    WorkspaceEntity createWorkspace(WorkspaceDTO workspaceDTO);
}
