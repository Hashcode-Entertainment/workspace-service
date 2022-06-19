package com.workspaceservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewWorkspaceDTO {
    private String owner;
    private String template;
    private Hooks hooks;

    public record Hooks(UpdateHookDTO onUpdate) {
    }
}
