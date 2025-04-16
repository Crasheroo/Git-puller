package com.group.Github_Puller.repository;

import com.group.Github_Puller.model.RepositoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepoRepository extends JpaRepository<RepositoryEntity, Long> {
    Optional<RepositoryEntity> findByFullName(String fullName);
}
