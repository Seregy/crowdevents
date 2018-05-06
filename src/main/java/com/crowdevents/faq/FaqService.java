package com.crowdevents.faq;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FaqService {
    Faq create(Long projectId, String question, String answer);

    Optional<Faq> get(Long id);

    Page<Faq> getAll(Pageable pageable);

    Page<Faq> getAllByProject(Long projectId, Pageable pageable);

    boolean delete(Long id);

    void update(Long id, Faq updatedFaq);
}
