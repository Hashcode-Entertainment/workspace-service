package com.workspaceservice.interfaces;

import com.workspaceservice.dao.WorkspaceDAO;
import com.workspaceservice.dto.AddFilesRequestDTO;
import com.workspaceservice.dto.NewWorkspaceDTO;

public interface IGitService {
    WorkspaceDAO createWorkspace(NewWorkspaceDTO newWorkspaceDTO);

    WorkspaceDAO addFileToWorkspace(String template, AddFilesRequestDTO jsonRequestDTO);
}
