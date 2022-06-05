package com.workspaceservice.services;

import com.workspaceservice.dao.WorkspaceEntity;
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
    public WorkspaceEntity createWorkspace(WorkspaceDTO workspaceDTO) {
        WorkspaceEntity newWorkspace = new WorkspaceEntity(
                workspaceDTO.getOwner(),
                workspaceDTO.getTemplate()
        );
        workspaceRepository.save(newWorkspace);
        return workspaceRepository.getReferenceById(newWorkspace.getId());
    }
}
