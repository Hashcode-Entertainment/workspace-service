package com.workspaceservice.exceptions;

import java.io.IOException;

public class NetworkException extends Exception {
    public NetworkException(IOException cause) {
        super(cause);
    }
}
