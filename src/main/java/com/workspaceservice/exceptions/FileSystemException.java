package com.workspaceservice.exceptions;

import java.io.IOException;

public class FileSystemException extends Exception {
    public FileSystemException(IOException cause) {
        super(cause);
    }
}
