package com.crowdevents.project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProjectRepository extends PagingAndSortingRepository<Project, Long> {
    Page<Project> findAllByIdAfterAndIdBefore(Long afterId, Long beforeId, Pageable pageable);

    Page<Project> findAllByIdAfter(Long afterId, Pageable pageable);

    Page<Project> findAllByIdBefore(Long beforeId, Pageable pageable);
}
