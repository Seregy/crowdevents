package com.crowdevents.update;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UpdateService {
    Update post(Long projectId, String title, String message);

    Optional<Update> get(Long id);

    Page<Update> getAll(Pageable pageable);

    Page<Update> getAllByProject(Long projectId, Pageable pageable);

    boolean delete(Long id);

    void update(Long id, Update updatedUpdate);
}
