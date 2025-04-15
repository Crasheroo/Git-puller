package com.group.Github_Puller.service;

import com.group.Github_Puller.feign.GithubClient;
import com.group.Github_Puller.mapper.RepositoryMapper;
import com.group.Github_Puller.model.RepositoryDTO;
import com.group.Github_Puller.model.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class RepositoryService {
    private final GithubClient githubClient;
    private final RepositoryMapper repositoryMapper;

    public RepositoryDTO getRepo(String owner, String repoName) throws Exception {
        Repository repository = githubClient.getRepo(owner, repoName);

        return repositoryMapper.toDTO(repository);
    }
}

