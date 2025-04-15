package com.group.Github_Puller.service;

import com.group.Github_Puller.feign.GithubClient;
import com.group.Github_Puller.mapper.RepoMapper;
import com.group.Github_Puller.model.GithubRepoResponse;
import com.group.Github_Puller.model.Repo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class RepoService {
    private final GithubClient githubClient;
    private final RepoMapper repoMapper;

    public GithubRepoResponse getRepo(String owner, String repoName) throws Exception {
        Repo repo = githubClient.getRepo(owner, repoName);

        return repoMapper.toDTO(repo);
    }
}

