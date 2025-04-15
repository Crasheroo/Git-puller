package com.group.Github_Puller.feign;

import com.group.Github_Puller.model.Repo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "github", url = "https://api.github.com")
public interface GithubClient {
    @GetMapping("/repos/{owner}/{repo}")
    Repo getRepo(@PathVariable("owner") String owner, @PathVariable("repo") String repo) throws Exception;
}