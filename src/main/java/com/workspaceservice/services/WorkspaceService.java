package com.workspaceservice.services;

import com.workspaceservice.dao.Workspace;
import com.workspaceservice.dto.WorkspaceDTO;
import com.workspaceservice.interfaces.IWorkspaceService;
import com.workspaceservice.repositories.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkspaceService implements IWorkspaceService {
    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Override
    public Workspace createWorkspace(WorkspaceDTO workspaceDTO) {
        Workspace newWorkspace = new Workspace(
                workspaceDTO.getOwner(),
                workspaceDTO.getTemplate()
        );
        workspaceRepository.save(newWorkspace);
        return workspaceRepository.getReferenceById(newWorkspace.getId());
    }
}
