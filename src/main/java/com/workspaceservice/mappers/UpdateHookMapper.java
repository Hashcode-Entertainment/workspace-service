package com.workspaceservice.mappers;

import com.workspaceservice.dao.UpdateHookDAO;
import com.workspaceservice.dto.UpdateHookDTO;
import com.workspaceservice.domain.UpdateHook;

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
                updateHookDAO.getWorkspaceUrl()
        );
    }

    public static UpdateHook toUpdateHook(UpdateHookDTO updateHookDTO) {
        if (updateHookDTO == null) {
            return null;
        }

        return new UpdateHook(updateHookDTO.getFiles(), updateHookDTO.getWorkspaceUrl());
    }

    public static UpdateHookDAO toUpdateHookDao(UpdateHook updateHook) {
        if (updateHook == null) {
            return null;
        }

        return new UpdateHookDAO(
                updateHook.getFiles(),
                updateHook.getUrl()
        );
    }

    public static UpdateHookDTO toUpdateHookDto(UpdateHook updateHook) {
        if (updateHook == null) {
            return null;
        }

        return new UpdateHookDTO(
                updateHook.getFiles(),
                updateHook.getUrl()
        );
    }
}
