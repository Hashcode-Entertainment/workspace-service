package com.workspaceservice.services;

import com.workspaceservice.Workspace;
import com.workspaceservice.WorkspaceManager;
import com.workspaceservice.dto.AddFilesRequestDTO;
import com.workspaceservice.dto.NewWorkspaceDTO;
import com.workspaceservice.dto.WorkspaceDTO;
import com.workspaceservice.exceptions.FileSystemException;
import com.workspaceservice.git.GitServer;
import com.workspaceservice.interfaces.IWorkspaceService;
import com.workspaceservice.mappers.WorkspaceMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class WorkspaceService implements IWorkspaceService {
    private final WorkspaceManager workspaceManager;
    private final GitServer gitServer;

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

    public void addFiles(String workspaceId, List<AddFilesRequestDTO> addFilesList) throws FileSystemException {
        var commitBuilder = gitServer.createCommitBuilder(workspaceId);
        for (AddFilesRequestDTO addFilesRequestDTO : addFilesList) {
            var path = addFilesRequestDTO.getPath();
            var content = addFilesRequestDTO.getContent();
            commitBuilder.addFile(Path.of(path), content);
        }
            commitBuilder.commit("New commit", "master", "sages", "sages@sages.com");
    }
}
