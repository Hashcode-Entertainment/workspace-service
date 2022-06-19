package com.workspaceservice.git;

import java.util.List;

public interface PostReceiveHook {
    void run(List<String> updatedRefs, String repoId);
}
