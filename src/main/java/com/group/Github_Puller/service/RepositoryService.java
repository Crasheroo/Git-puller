package com.group.Github_Puller.service;

import com.group.Github_Puller.exception.RepoException;
import com.group.Github_Puller.feign.GithubClient;
import com.group.Github_Puller.mapper.RepositoryMapper;
import com.group.Github_Puller.model.Repository;
import com.group.Github_Puller.model.RepositoryDTO;
import com.group.Github_Puller.model.RepositoryEntity;
import com.group.Github_Puller.repository.RepoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RepositoryService {
    private final GithubClient githubClient;
    private final RepositoryMapper repositoryMapper;
    private final RepoRepository repoRepository;

    public RepositoryDTO getRepo(String owner, String repoName) throws Exception {
        return repositoryMapper.toDTO(githubClient.getRepo(owner, repoName));
    }

    public RepositoryDTO getRepoFromDb(String owner, String repoName) {
        String fullName = owner + "/" + repoName;
        RepositoryEntity repo = repoRepository.findByFullName(fullName)
                .orElseThrow(() -> new RepoException("Repo not found", HttpStatus.NOT_FOUND));
        return repositoryMapper.toDTO(repo);
    }

    @Transactional
    public RepositoryDTO saveRepoDetails(String owner, String repoName) throws Exception {
        String fullName = (owner + "/" + repoName);
        repoRepository.findByFullName(fullName)
                .ifPresent(existing -> { throw new RepoException("Repo already exists", HttpStatus.CONFLICT); });

        Repository repo = githubClient.getRepo(owner, repoName);

        RepositoryDTO build = RepositoryDTO.builder()
                .fullName(repo.getFullName())
                .description(repo.getDescription())
                .cloneUrl(repo.getCloneUrl())
                .createdAt(repo.getCreatedAt())
                .build();


        RepositoryEntity savedEntity = repoRepository.save(repositoryMapper.toEntity(build));

        return repositoryMapper.toDTO(savedEntity);
    }

    @Transactional
    public RepositoryDTO updateRepoDetails(String owner, String repoName, RepositoryDTO repoDTO) {
        String fullName = owner + "/" + repoName;

        RepositoryEntity existingRepo = repoRepository.findByFullName(fullName)
                .orElseThrow(() -> new RepoException("Repository not found: " + fullName, HttpStatus.NOT_FOUND));

        repoValidation(repoDTO, existingRepo);

        RepositoryEntity updatedRepo = repoRepository.save(existingRepo);
        return repositoryMapper.toDTO(updatedRepo);
    }

    @Transactional
    public void deleteRepoFromDb(String owner, String repoName) {
        String fullName = (owner + "/" + repoName);

        RepositoryEntity repoToDelete = repoRepository.findByFullName(fullName)
                .orElseThrow(() -> new RepoException("Repository not found: " + fullName, HttpStatus.NOT_FOUND));

        repoRepository.delete(repoToDelete);
    }

    private void repoValidation(RepositoryDTO repoDTO, RepositoryEntity existingRepo) {
        if (repoDTO.getDescription() != null) {
            existingRepo.setDescription(repoDTO.getDescription());
        }
        if (repoDTO.getCloneUrl() != null) {
            existingRepo.setCloneUrl(repoDTO.getCloneUrl());
        }
        if (repoDTO.getStars() != null) {
            existingRepo.setStars(repoDTO.getStars());
        }
        if (repoDTO.getCreatedAt() != null) {
            existingRepo.setCreatedAt(repoDTO.getCreatedAt());
        }
    }
}

