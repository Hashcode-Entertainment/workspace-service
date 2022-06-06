package com.workspaceservice.dto;

import com.workspaceservice.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NewWorkspaceDTO {
    private User owner;
    private String template;
}
