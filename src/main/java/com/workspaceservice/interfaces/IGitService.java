package com.workspaceservice.interfaces;

import com.workspaceservice.dao.Workspace;
import com.workspaceservice.dto.AdminRequestDTO;
import com.workspaceservice.dto.JsonRequestDTO;
import com.workspaceservice.dto.UserRequestDTO;

public interface IGitService {
    Workspace createAdminRepo(AdminRequestDTO adminRequestDTO);
    Workspace createUserRepo(UserRequestDTO userRequestDTO);
    Workspace addFile(JsonRequestDTO jsonRequestDTO);
}
