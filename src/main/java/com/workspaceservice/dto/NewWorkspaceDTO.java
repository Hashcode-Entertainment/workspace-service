package com.workspaceservice.dto;

import com.workspaceservice.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NewWorkspaceDTO {
    private String owner;
    private String template;
    private UpdateHookDTO updateHook;
}
