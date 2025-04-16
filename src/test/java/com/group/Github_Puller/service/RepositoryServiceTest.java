package com.group.Github_Puller.service;

import com.group.Github_Puller.exception.RepoException;
import com.group.Github_Puller.feign.GithubClient;
import com.group.Github_Puller.mapper.RepositoryMapper;
import com.group.Github_Puller.model.Repository;
import com.group.Github_Puller.model.RepositoryDTO;
import com.group.Github_Puller.model.RepositoryEntity;
import com.group.Github_Puller.repository.RepoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RepositoryServiceTest {
    private GithubClient githubClient;
    private RepositoryService repoService;
    private RepositoryMapper repoMapper;
    private RepoRepository repoRepository;

    @BeforeEach
    void setUp() {
        this.githubClient = Mockito.mock(GithubClient.class);
        this.repoRepository = Mockito.mock(RepoRepository.class);
        this.repoMapper = Mappers.getMapper(RepositoryMapper.class);
        this.repoService = new RepositoryService(githubClient, repoMapper, repoRepository);
    }

    @Test
    void getRepo_ShouldReturnRepositoryDTO() throws Exception {
        // Given
        String owner = "testOwner";
        String repoName = "testRepo";
        Repository githubRepo = new Repository();
        RepositoryDTO expectedDto = repoMapper.toDTO(githubRepo);

        when(githubClient.getRepo(owner, repoName)).thenReturn(githubRepo);

        // When
        RepositoryDTO result = repoService.getRepo(owner, repoName);

        // Then
        assertNotNull(result);
        assertEquals(expectedDto.getFullName(), result.getFullName());
        verify(githubClient).getRepo(owner, repoName);
    }

    @Test
    void getRepo_ShouldThrowExceptionWhenClientFails() throws Exception {
        // Given
        String owner = "testOwner";
        String repoName = "testRepo";

        when(githubClient.getRepo(owner, repoName)).thenThrow(new RuntimeException("API Error"));

        // When/Then
        assertThrows(Exception.class, () -> repoService.getRepo(owner, repoName));
    }

    @Test
    void getRepoFromDb_ShouldReturnRepositoryDTO() {
        // Given
        String owner = "testOwner";
        String repoName = "testRepo";
        String fullName = owner + "/" + repoName;
        RepositoryEntity entity = new RepositoryEntity();
        RepositoryDTO expectedDto = repoMapper.toDTO(entity);

        when(repoRepository.findByFullName(fullName)).thenReturn(Optional.of(entity));

        // When
        RepositoryDTO result = repoService.getRepoFromDb(owner, repoName);

        // Then
        assertNotNull(result);
        assertEquals(expectedDto.getFullName(), result.getFullName());
        verify(repoRepository).findByFullName(fullName);
    }

    @Test
    void getRepoFromDb_ShouldThrowExceptionWhenNotFound() {
        // Given
        String owner = "testOwner";
        String repoName = "testRepo";
        String fullName = owner + "/" + repoName;

        when(repoRepository.findByFullName(fullName)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(RepoException.class, () -> repoService.getRepoFromDb(owner, repoName));
    }

    @Test
    void saveRepoDetails_ShouldSaveNewRepository() throws Exception {
        // Given
        String owner = "testOwner";
        String repoName = "testRepo";
        String fullName = owner + "/" + repoName;
        Repository githubRepo = new Repository();
        RepositoryEntity savedEntity = new RepositoryEntity();

        when(repoRepository.findByFullName(fullName)).thenReturn(Optional.empty());
        when(githubClient.getRepo(owner, repoName)).thenReturn(githubRepo);
        when(repoRepository.save(any())).thenReturn(savedEntity);

        // When
        RepositoryDTO result = repoService.saveRepoDetails(owner, repoName);

        // Then
        assertNotNull(result);
        verify(repoRepository).findByFullName(fullName);
        verify(githubClient).getRepo(owner, repoName);
        verify(repoRepository).save(any());
    }

    @Test
    void saveRepoDetails_ShouldThrowExceptionWhenRepoExists() {
        // Given
        String owner = "testOwner";
        String repoName = "testRepo";
        String fullName = owner + "/" + repoName;
        RepositoryEntity existingEntity = new RepositoryEntity();

        when(repoRepository.findByFullName(fullName)).thenReturn(Optional.of(existingEntity));

        // When/Then
        assertThrows(RepoException.class, () -> repoService.saveRepoDetails(owner, repoName));
    }

    @Test
    void updateRepoDetails_ShouldUpdateExistingRepository() {
        // Given
        String owner = "testOwner";
        String repoName = "testRepo";
        String fullName = owner + "/" + repoName;
        RepositoryDTO repoDTO = new RepositoryDTO();
        RepositoryEntity existingEntity = new RepositoryEntity();
        RepositoryEntity updatedEntity = new RepositoryEntity();

        when(repoRepository.findByFullName(fullName)).thenReturn(Optional.of(existingEntity));
        when(repoRepository.save(existingEntity)).thenReturn(updatedEntity);

        // When
        RepositoryDTO result = repoService.updateRepoDetails(owner, repoName, repoDTO);

        // Then
        assertNotNull(result);
        verify(repoRepository).findByFullName(fullName);
        verify(repoRepository).save(existingEntity);
    }

    @Test
    void updateRepoDetails_ShouldThrowExceptionWhenNotFound() {
        // Given
        String owner = "testOwner";
        String repoName = "testRepo";
        String fullName = owner + "/" + repoName;
        RepositoryDTO repoDTO = new RepositoryDTO();

        when(repoRepository.findByFullName(fullName)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(RepoException.class, () -> repoService.updateRepoDetails(owner, repoName, repoDTO));
    }

    @Test
    void deleteRepoFromDb_ShouldDeleteRepository() {
        // Given
        String owner = "testOwner";
        String repoName = "testRepo";
        String fullName = owner + "/" + repoName;
        RepositoryEntity entityToDelete = new RepositoryEntity();

        when(repoRepository.findByFullName(fullName)).thenReturn(Optional.of(entityToDelete));
        doNothing().when(repoRepository).delete(entityToDelete);

        // When
        repoService.deleteRepoFromDb(owner, repoName);

        // Then
        verify(repoRepository).findByFullName(fullName);
        verify(repoRepository).delete(entityToDelete);
    }

    @Test
    void deleteRepoFromDb_ShouldThrowExceptionWhenNotFound() {
        // Given
        String owner = "testOwner";
        String repoName = "testRepo";
        String fullName = owner + "/" + repoName;

        when(repoRepository.findByFullName(fullName)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(RepoException.class, () -> repoService.deleteRepoFromDb(owner, repoName));
    }
}
