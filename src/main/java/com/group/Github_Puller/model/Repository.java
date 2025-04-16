package com.group.Github_Puller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Repository {
    @JsonProperty("full_name") // nazwa z api
    private String fullName;
    private String description;
    @JsonProperty("clone_url")
    private String cloneUrl;
    @JsonProperty("stargazers_count")
    private Integer stars;
    @JsonProperty("created_at")
    private LocalDate createdAt;
}