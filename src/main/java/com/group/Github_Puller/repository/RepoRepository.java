package com.group.Github_Puller.repository;

import com.group.Github_Puller.model.Repo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoRepository extends JpaRepository<Repo, Long> {
}