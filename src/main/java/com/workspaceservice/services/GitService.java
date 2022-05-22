package com.workspaceservice.services;

import com.workspaceservice.dao.Workspace;
import com.workspaceservice.dto.AdminRequestDTO;
import com.workspaceservice.dto.JsonRequestDTO;
import com.workspaceservice.dto.UserRequestDTO;
import com.workspaceservice.interfaces.IGitService;
import org.springframework.stereotype.Service;

@Service
public class GitService implements IGitService {
    //TODO
    @Override
    public Workspace createAdminRepo(AdminRequestDTO adminRequestDTO) {
        return null;
    }

    //TODO
    @Override
    public Workspace createUserRepo(UserRequestDTO userRequestDTO) {
        return null;
    }
    //TODO
    @Override
    public Workspace addFile(JsonRequestDTO jsonRequestDTO) {
        return null;
    }

}
