package com.workspaceservice.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workspaceservice.dto.UpdateHookCallbackDTO;
import com.workspaceservice.exceptions.FileSystemException;
import com.workspaceservice.exceptions.NetworkException;
import com.workspaceservice.git.GitServer;
import com.workspaceservice.mappers.WorkspaceMapper;
import com.workspaceservice.repositories.WorkspaceRepository;
import com.workspaceservice.user.User;
import lombok.SneakyThrows;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.workspaceservice.git.GitUtils.resolveBranchRef;
import static com.workspaceservice.utils.UrlUtils.resolveUrl;


@Component
public class WorkspaceManager {
    private final GitServer gitServer;
    private final URL gitServerUrl;
    private final WorkspaceRepository workspaceRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public WorkspaceManager(
            GitServer gitServer,
            @Qualifier("gitServer") URL gitServerUrl,
            WorkspaceRepository workspaceRepository,
            ObjectMapper objectMapper
    ) {
        this.gitServer = gitServer;
        this.gitServerUrl = gitServerUrl;
        this.workspaceRepository = workspaceRepository;
        this.objectMapper = objectMapper;

        gitServer.onPostReceive(this::onPostReceive);
    }

    public Workspace createWorkspace(@NotNull User owner, @Nullable Workspace template, @Nullable UpdateHook updateHook)
            throws FileSystemException {

        var id = UUID.randomUUID();
        var templateId = template != null ? template.id() : null;

        String repoPath;
        if (template == null) {
            repoPath = gitServer.createRepo(id.toString());
        } else {
            repoPath = gitServer.forkRepo(templateId.toString(), id.toString());
        }

        var repoUrl = resolveUrl(gitServerUrl, repoPath);

        var workspace = new Workspace(id, owner.id(), templateId, repoUrl, updateHook);
        var workspaceDao = WorkspaceMapper.toWorkspaceDao(workspace);
        workspaceRepository.save(workspaceDao);

        return workspace;
    }

    public Workspace getWorkspace(UUID id) {
        return WorkspaceMapper.toWorkspace(workspaceRepository.findById(id).orElse(null));
    }

    public void deleteWorkspace(@NotNull UUID id) throws FileSystemException {
        gitServer.deleteRepo(id.toString());
    }

    private static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    @SneakyThrows({FileSystemException.class, NetworkException.class})
    private void onPostReceive(List<String> updatedRefs, String repoId) {
        if (!updatedRefs.contains(resolveBranchRef("master"))) {
            return;
        }

        var workspaceId = UUID.fromString(repoId);
        var workspaceDao = workspaceRepository.findById(workspaceId).orElse(null);
        var workspace = WorkspaceMapper.toWorkspace(workspaceDao);
        if (workspace == null || workspace.updateHook() == null) {
            return;
        }

        var newContents = new HashMap<String, String>();
        for (Path file : workspace.updateHook().getFiles()) {
            newContents.put(file.toString(), gitServer.getFileContent(repoId, file, "master"));
        }

        var dto = new UpdateHookCallbackDTO(newContents, workspace.id().toString());
        String json;
        try {
            json = objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new AssertionError(e);
        }
        var body = RequestBody.create(json, JSON);
        var client = new OkHttpClient();
        var request = new Request.Builder()
                .url(workspace.updateHook().getUrl())
                .post(body)
                .build();

        try {
            client.newCall(request).execute().close();
        } catch (IOException e) {
            throw new NetworkException(e);
        }

        System.out.println("POSTED TO " + workspace.updateHook().getUrl());
    }
}
