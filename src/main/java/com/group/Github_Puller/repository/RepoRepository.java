package com.group.Github_Puller.repository;

import com.group.Github_Puller.model.Repo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepoRepository extends JpaRepository<Repo, Long> {
    Optional<Repo> findByFullName(String fullName);
    boolean existsByFullName(String fullName);
}