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
}
