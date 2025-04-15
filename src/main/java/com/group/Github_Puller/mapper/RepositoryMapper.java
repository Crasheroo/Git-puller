package com.group.Github_Puller.mapper;

import com.group.Github_Puller.model.RepositoryDTO;
import com.group.Github_Puller.model.Repository;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RepositoryMapper {
    RepositoryDTO toDTO(Repository repo);
}
