package com.workspaceservice.interfaces;

import com.workspaceservice.dao.WorkspaceEntity;
import com.workspaceservice.dto.NewWorkspaceDTO;
import com.workspaceservice.dto.WorkspaceDTO;

public interface IWorkspaceService {
    WorkspaceDTO createWorkspace(NewWorkspaceDTO newWorkspaceDTO);
}
