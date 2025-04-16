package com.group.Github_Puller.exception;

import org.springframework.http.HttpStatus;

public class RepoException extends RuntimeException {
    private final HttpStatus status;
    public RepoException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
