package com.workspaceservice.interfaces;

import com.workspaceservice.dto.AddFilesRequestDTO;
import com.workspaceservice.dto.NewWorkspaceDTO;
import com.workspaceservice.dto.WorkspaceDTO;
import com.workspaceservice.exceptions.FileSystemException;

import java.util.List;

public interface IWorkspaceService {
    WorkspaceDTO createWorkspace(NewWorkspaceDTO newWorkspaceDTO) throws FileSystemException;
    void addFiles(String workspaceId, List<AddFilesRequestDTO> addFilesList) throws FileSystemException;
}
