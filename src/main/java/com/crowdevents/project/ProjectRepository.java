package com.crowdevents.project;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ProjectRepository extends PagingAndSortingRepository<Project, UUID> {
}
