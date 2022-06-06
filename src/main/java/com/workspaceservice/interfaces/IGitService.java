package com.workspaceservice.interfaces;

import com.workspaceservice.dao.WorkspaceEntity;
import com.workspaceservice.dto.AddFilesRequestDTO;
import com.workspaceservice.dto.NewWorkspaceDTO;

public interface IGitService {
    WorkspaceEntity createWorkspace(NewWorkspaceDTO newWorkspaceDTO);

    WorkspaceEntity addFileToWorkspace(String template, AddFilesRequestDTO jsonRequestDTO);
}
