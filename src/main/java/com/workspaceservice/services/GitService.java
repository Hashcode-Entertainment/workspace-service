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
    public Workspace createAdminWorkspace(CreateWorkspaceRequestDTO adminRequestDTO) {
        return null;
    }

    //TODO
    @Override
    public Workspace addFileToWorkspace(AddFilesRequestDTO jsonRequestDTO) {
        return null;
    }

}
