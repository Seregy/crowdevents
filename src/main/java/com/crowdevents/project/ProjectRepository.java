package com.crowdevents.project;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProjectRepository extends PagingAndSortingRepository<Project, UUID> {
}
