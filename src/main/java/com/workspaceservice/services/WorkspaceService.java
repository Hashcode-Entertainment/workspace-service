package com.workspaceservice.services;

import com.workspaceservice.domain.Workspace;
import com.workspaceservice.domain.WorkspaceManager;
import com.workspaceservice.dto.AddFilesRequestDTO;
import com.workspaceservice.dto.NewWorkspaceDTO;
import com.workspaceservice.dto.WorkspaceDTO;
import com.workspaceservice.exceptions.FileSystemException;
import com.workspaceservice.exceptions.NoSuchWorkspaceException;
import com.workspaceservice.git.GitServer;
import com.workspaceservice.interfaces.IWorkspaceService;
import com.workspaceservice.mappers.UpdateHookMapper;
import com.workspaceservice.mappers.WorkspaceMapper;
import com.workspaceservice.user.User;
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
    public WorkspaceDTO createWorkspace(NewWorkspaceDTO newWorkspaceDTO)
            throws FileSystemException, NoSuchWorkspaceException {

        Workspace template = null;
        if (newWorkspaceDTO.getTemplate() != null) {
            var templateId = UUID.fromString(newWorkspaceDTO.getTemplate());
            template = workspaceManager.getWorkspace(templateId);
            if (template == null) {
                throw new NoSuchWorkspaceException(templateId);
            }
        }

        var updateHookDto = newWorkspaceDTO.getUpdateHook();
        var updateHook = UpdateHookMapper.toUpdateHook(updateHookDto);

        var workspace = workspaceManager.createWorkspace(
                new User(newWorkspaceDTO.getOwner()),
                template,
                updateHook
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
