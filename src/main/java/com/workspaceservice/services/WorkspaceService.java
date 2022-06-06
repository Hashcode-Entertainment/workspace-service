package com.workspaceservice.services;

import com.workspaceservice.Workspace;
import com.workspaceservice.WorkspaceManager;
import com.workspaceservice.dto.NewWorkspaceDTO;
import com.workspaceservice.dto.WorkspaceDTO;
import com.workspaceservice.exceptions.FileSystemException;
import com.workspaceservice.interfaces.IWorkspaceService;
import com.workspaceservice.mappers.WorkspaceMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class WorkspaceService implements IWorkspaceService {
    private final WorkspaceManager workspaceManager;

    @Override
    public WorkspaceDTO createWorkspace(NewWorkspaceDTO newWorkspaceDTO) throws FileSystemException {
        Workspace template = null;
        if (newWorkspaceDTO.getTemplate() != null) {
            var templateId = UUID.fromString(newWorkspaceDTO.getTemplate());
            template = workspaceManager.getWorkspace(templateId);
        }
        var workspace = workspaceManager.createWorkspace(
                newWorkspaceDTO.getOwner(),
                template
        );

        return WorkspaceMapper.toWorkspaceDTO(workspace);
    }
}
