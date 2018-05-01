package com.crowdevents.project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProjectRepository extends PagingAndSortingRepository<Project, Long> {
    Page<Project> findAllByTypeAndVisibility(ProjectType type,
                                             ProjectVisibility visibility, Pageable pageable);

    Page<Project> findAllByIdAfterAndIdBefore(Long afterId, Long beforeId, Pageable pageable);

    Page<Project> findAllByIdAfterAndIdBeforeAndTypeAndVisibility(Long afterId, Long beforeId,
                                                                  ProjectType type,
                                                                  ProjectVisibility visibility,
                                                                  Pageable pageable);

    Page<Project> findAllByIdAfter(Long afterId, Pageable pageable);

    Page<Project> findAllByIdAfterAndTypeAndVisibility(Long afterId, ProjectType type,
                                                       ProjectVisibility visibility,
                                                       Pageable pageable);

    Page<Project> findAllByIdBefore(Long beforeId, Pageable pageable);

    Page<Project> findAllByIdBeforeAndTypeAndVisibility(Long beforeId, ProjectType type,
                                                        ProjectVisibility visibility,
                                                        Pageable pageable);
}
