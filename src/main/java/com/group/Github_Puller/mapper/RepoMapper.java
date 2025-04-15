package com.group.Github_Puller.mapper;

import com.group.Github_Puller.model.GithubRepoResponse;
import com.group.Github_Puller.model.Repo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RepoMapper {
    GithubRepoResponse toDTO(Repo repo);
}
