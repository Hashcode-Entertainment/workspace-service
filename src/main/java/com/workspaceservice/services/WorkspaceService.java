package com.workspaceservice.services;

import com.workspaceservice.interfaces.IWorkspaceService;
import com.workspaceservice.repositories.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkspaceService implements IWorkspaceService {
    @Autowired
    private WorkspaceRepository workspaceRepository;
}
