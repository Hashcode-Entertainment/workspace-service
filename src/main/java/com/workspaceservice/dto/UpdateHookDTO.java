package com.workspaceservice.dto;

import com.workspaceservice.dao.RepoFileDAO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class UpdateHookDTO {
    private List<Path> files = new ArrayList<>();
    private String workspaceUrl;
}
