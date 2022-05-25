package com.workspaceservice.interfaces;

import com.workspaceservice.dao.Workspace;
import com.workspaceservice.dto.WorkspaceDTO;
import com.workspaceservice.dto.AddFilesRequestDTO;

public interface IGitService {
    Workspace createWorkspace(WorkspaceDTO workspaceDTO);
    Workspace addFileToWorkspace(String template, AddFilesRequestDTO jsonRequestDTO);
}
