package com.workspaceservice.services;

import com.workspaceservice.dao.Workspace;
import com.workspaceservice.dto.CreateWorkspaceRequestDTO;
import com.workspaceservice.dto.AddFilesRequestDTO;
import com.workspaceservice.interfaces.IGitService;
import org.springframework.stereotype.Service;

@Service
public class GitService implements IGitService {
    //TODO
    @Override
    public Workspace createWorkspace(CreateWorkspaceRequestDTO adminRequestDTO) {
        return null;
    }

    //TODO
    @Override
    public Workspace addFileToWorkspace(String template, AddFilesRequestDTO jsonRequestDTO) {
        return null;
    }

}