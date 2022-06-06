package com.workspaceservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddFilesRequestDTO {
    private String path;
    private String content;
}
