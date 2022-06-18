package com.workspaceservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class UpdateHook {
        private List<Path> files = new ArrayList<>();
        private String url;
}
