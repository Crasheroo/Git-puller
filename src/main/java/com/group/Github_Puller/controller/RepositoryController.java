package com.group.Github_Puller.controller;

import com.group.Github_Puller.model.RepositoryDTO;
import com.group.Github_Puller.service.RepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/repositories")
public class RepositoryController {
    private final RepositoryService repoService;

    @GetMapping("/{owner}/{repo}")
    public RepositoryDTO getRepo(@PathVariable String owner, @PathVariable String repo) throws Exception {
        return repoService.getRepo(owner, repo);
    }

    @GetMapping("/local/{owner}/{repo}")
    public RepositoryDTO getLocalRepo(@PathVariable String owner, @PathVariable String repo) {
        return repoService.getRepoFromDb(owner, repo);
    }

    @PutMapping("/{owner}/{repo}")
    public RepositoryDTO updateRepo(@PathVariable String owner, @PathVariable String repo, @RequestBody RepositoryDTO repositoryDTO) {
        return repoService.updateRepoDetails(owner, repo, repositoryDTO);
    }

    @PostMapping("/{owner}/{repo}")
    public RepositoryDTO saveRepoDetails(@PathVariable String owner, @PathVariable String repo) throws Exception {
        return repoService.saveRepoDetails(owner, repo);
    }

    @DeleteMapping("/{owner}/{repo}")
    public void deleteRepo(@PathVariable String owner, @PathVariable String repo) {
        repoService.deleteRepoFromDb(owner, repo);
    }
}
