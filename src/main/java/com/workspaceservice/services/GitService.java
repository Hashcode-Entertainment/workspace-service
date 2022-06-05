package com.workspaceservice.services;

import com.workspaceservice.dao.WorkspaceEntity;
import com.workspaceservice.dto.AddFilesRequestDTO;
import com.workspaceservice.dto.WorkspaceDTO;
import com.workspaceservice.interfaces.IGitService;
import org.springframework.stereotype.Service;

@Service
public class GitService implements IGitService {
    //TODO
    @Override
    public WorkspaceEntity createWorkspace(WorkspaceDTO workspaceDTO) {
        return null;
    }

    //TODO
    @Override
    public WorkspaceEntity addFileToWorkspace(String template, AddFilesRequestDTO jsonRequestDTO) {
        return null;
    }
}
