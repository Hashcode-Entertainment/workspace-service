package com.workspaceservice.mappers;

import com.workspaceservice.dao.RepoFileDAO;
import com.workspaceservice.domain.RepoFile;
import com.workspaceservice.dto.RepoFileDTO;

public abstract class RepoFileMapper {
    public static RepoFile toRepoFile(RepoFileDAO repoFileDAO) {
        if (repoFileDAO == null) {
            return null;
        }

        return new RepoFile(repoFileDAO.getFilePath());
    }

    public static RepoFile toRepoFile(RepoFileDTO repoFileDTO) {
        if (repoFileDTO == null) {
            return null;
        }

        return new RepoFile(repoFileDTO.getFilePath());
    }

    public static RepoFileDAO toRepoFileDao(RepoFile repoFile) {
        if (repoFile == null) {
            return null;
        }

        return new RepoFileDAO(
                repoFile.getFilePath()
        );
    }

    public static RepoFileDTO toRepoFileDto(RepoFile repoFile) {
        if (repoFile == null) {
            return null;
        }

        return new RepoFileDTO(
                repoFile.getFilePath()
        );
    }
}
