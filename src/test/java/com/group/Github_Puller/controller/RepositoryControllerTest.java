package com.group.Github_Puller.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.Github_Puller.model.RepositoryDTO;
import com.group.Github_Puller.service.RepositoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureMockMvc
public class RepositoryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private RepositoryService repositoryService;

    private final String BASE_URL = "/repositories";

    @Test
    public void getRepo_ShouldReturnRepoDTO() throws Exception {
        // Given
        String owner = "testOwner";
        String repoName = "testRepoName";
        RepositoryDTO expected = new RepositoryDTO();
        when(repositoryService.getRepo(owner, repoName)).thenReturn(expected);

        // When then
        mockMvc.perform(get(BASE_URL + "/{owner}/{repo}", owner, repoName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value(expected.getFullName()));
    }

    @Test
    public void getLocalRepo_ShouldReturnRepositoryDTOFromDb() throws Exception {
        // Given
        String owner = "testOwner";
        String repo = "testRepo";
        RepositoryDTO expectedDto = new RepositoryDTO();
        when(repositoryService.getRepoFromDb(owner, repo)).thenReturn(expectedDto);

        // When/Then
        mockMvc.perform(get(BASE_URL + "/local/{owner}/{repo}", owner, repo)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value(expectedDto.getFullName()));
    }

    @Test
    public void updateRepo_ShouldReturnUpdatedRepositoryDTO() throws Exception {
        // Given
        String owner = "Cymek12";
        String repo = "MedicalClinic";
        String fullName = owner + "/" + repo;

        RepositoryDTO requestDto = RepositoryDTO.builder()
                .description("Updated description")
                .stars(100)
                .build();

        RepositoryDTO expectedDto = RepositoryDTO.builder()
                .fullName(fullName)
                .description("Updated description")
                .cloneUrl("https://github.com/Cymek12/MedicalClinic.git")
                .stars(100)
                .createdAt(LocalDate.now())
                .build();

        when(repositoryService.updateRepoDetails(owner, repo, requestDto)).thenReturn(expectedDto);

        // When/Then
        mockMvc.perform(put(BASE_URL + "/{owner}/{repo}", owner, repo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.fullName").value(fullName))
                .andExpect(jsonPath("$.description").value("Updated description"))
                .andExpect(jsonPath("$.stars").value(100))
                .andExpect(jsonPath("$.cloneUrl").exists())
                .andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    public void saveRepoDetails_ShouldReturnSavedRepositoryDTO() throws Exception {
        // Given
        String owner = "testOwner";
        String repo = "testRepo";
        RepositoryDTO expectedDto = new RepositoryDTO();

        when(repositoryService.saveRepoDetails(owner, repo)).thenReturn(expectedDto);

        // When/Then
        mockMvc.perform(post(BASE_URL + "/{owner}/{repo}", owner, repo)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value(expectedDto.getFullName()));
    }

    @Test
    public void deleteRepo_ShouldReturnOk() throws Exception {
        // Given
        String owner = "testOwner";
        String repo = "testRepo";

        doNothing().when(repositoryService).deleteRepoFromDb(owner, repo);

        // When/Then
        mockMvc.perform(delete(BASE_URL + "/{owner}/{repo}", owner, repo))
                .andExpect(status().isOk());

        verify(repositoryService, times(1)).deleteRepoFromDb(owner, repo);
    }
}
