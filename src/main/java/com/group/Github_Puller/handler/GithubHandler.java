package com.group.Github_Puller.handler;

import com.group.Github_Puller.exception.RepoException;
import com.group.Github_Puller.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GithubHandler extends ResponseEntityExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RepoException.class)
    public ErrorMessage handleRepoException(RepoException ex) {
        return ErrorMessage.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .errorTime(LocalDateTime.now())
                .build();
    }
}
