package com.workspaceservice.services;

import com.workspaceservice.dao.WorkspaceEntity;
import com.workspaceservice.dto.NewWorkspaceDTO;
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
    public WorkspaceDTO createWorkspace(NewWorkspaceDTO newWorkspaceDTO) {
        WorkspaceEntity newWorkspace = new WorkspaceEntity(
                newWorkspaceDTO.getOwner(),
                newWorkspaceDTO.getTemplate()
        );
        workspaceRepository.save(newWorkspace);
        var entity = workspaceRepository.findById(newWorkspace.getId());
        return new WorkspaceDTO(entity.getId(), entity.getOwner().toString(), entity.getTemplate());
    }
}