package com.workspaceservice.interfaces;

import com.workspaceservice.dao.Workspace;
import com.workspaceservice.dto.CreateWorkspaceRequestDTO;
import com.workspaceservice.dto.AddFilesRequestDTO;

public interface IGitService {
    Workspace createAdminWorkspace(CreateWorkspaceRequestDTO adminRequestDTO);
    Workspace addFileToWorkspace(AddFilesRequestDTO jsonRequestDTO);
}
