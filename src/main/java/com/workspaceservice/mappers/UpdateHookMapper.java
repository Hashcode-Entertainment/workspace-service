package com.workspaceservice.mappers;

import com.workspaceservice.dao.RepoFileDAO;
import com.workspaceservice.dao.UpdateHookDAO;
import com.workspaceservice.domain.UpdateHook;
import com.workspaceservice.dto.UpdateHookDTO;

import java.nio.file.Paths;

public abstract class UpdateHookMapper {

    public static UpdateHook toUpdateHook(UpdateHookDAO updateHookDAO) {
        if (updateHookDAO == null) {
            return null;
        }

        return new UpdateHook(
                updateHookDAO
                        .getFiles()
                        .stream()
                        .map(repoFileDAO ->
                                Paths.get(repoFileDAO.getFilePath()))
                        .toList(),
                updateHookDAO.getUrl()
        );
    }

    public static UpdateHook toUpdateHook(UpdateHookDTO updateHookDTO) {
        if (updateHookDTO == null) {
            return null;
        }

        var files = updateHookDTO
                .getFiles()
                .stream()
                .map(Paths::get)
                .toList();

        return new UpdateHook(files, updateHookDTO.getUrl());
    }

    public static UpdateHookDAO toUpdateHookDao(UpdateHook updateHook) {
        if (updateHook == null) {
            return null;
        }

        var repoFileDAOList = updateHook.getFiles().stream().map(file ->
                        new RepoFileDAO(file.toString()))
                .toList();

        return new UpdateHookDAO(
                repoFileDAOList,
                updateHook.getUrl()
        );
    }
}
