package com.workspaceservice.interfaces;

import com.workspaceservice.dao.WorkspaceEntity;
import com.workspaceservice.dto.AddFilesRequestDTO;
import com.workspaceservice.dto.WorkspaceDTO;

public interface IGitService {
    WorkspaceEntity createWorkspace(WorkspaceDTO workspaceDTO);

    WorkspaceEntity addFileToWorkspace(String template, AddFilesRequestDTO jsonRequestDTO);
}
