package com.group.Github_Puller.controller;

import com.group.Github_Puller.model.GithubRepoResponse;
import com.group.Github_Puller.model.Repo;
import com.group.Github_Puller.service.RepoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/repositories")
public class RepoController {
    private final RepoService repoService;

    @GetMapping("/{owner}/{repo}")
    public ResponseEntity<GithubRepoResponse> getRepo(@PathVariable String owner, @PathVariable String repo) throws Exception {
        GithubRepoResponse response = repoService.getRepo(owner, repo);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{owner}/{repo}")
    public ResponseEntity<Repo> createRepo(@PathVariable String owner, @PathVariable String repo) {
        return null;
    }

    @DeleteMapping("/{owner}/{repo}")
    public void deleteRepo(@PathVariable String owner, @PathVariable String repo) {
        repoService.deleteRepo(owner, repo);
    }
}
