package com.workspaceservice.dto;

import java.util.Map;

public record UpdateHookCallbackDTO(Map<String, String> newContents, String workspace) {
}
