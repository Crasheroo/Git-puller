package com.group.Github_Puller.model;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RepositoryDTO {
    private String fullName;
    private String description;
    private String cloneUrl;
    private Integer stars;
    private LocalDate createdAt;
}