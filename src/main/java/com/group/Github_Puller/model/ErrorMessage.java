package com.group.Github_Puller.model;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
public record ErrorMessage
        (Integer status,
         HttpStatus error,
         String message,
         LocalDateTime timestamp
        ) {
}
