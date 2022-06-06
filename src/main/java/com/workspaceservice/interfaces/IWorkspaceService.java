package com.workspaceservice.interfaces;

import com.workspaceservice.dto.NewWorkspaceDTO;
import com.workspaceservice.dto.WorkspaceDTO;
import com.workspaceservice.exceptions.FileSystemException;

public interface IWorkspaceService {
    WorkspaceDTO createWorkspace(NewWorkspaceDTO newWorkspaceDTO) throws FileSystemException;
}
