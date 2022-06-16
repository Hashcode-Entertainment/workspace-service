package com.workspaceservice.exceptions;

import java.util.UUID;

public class NoSuchWorkspaceException extends Exception {
    public NoSuchWorkspaceException(UUID uuidOfMissingWorkspace) {
        super("No such workspace: " + uuidOfMissingWorkspace);
    }
}
