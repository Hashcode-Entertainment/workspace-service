package com.workspaceservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class WorkspaceDTO {
    private UUID id;
    private String owner;
    private String template;
    private String url;
}
