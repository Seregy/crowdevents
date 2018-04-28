package com.crowdevents.faq;

import java.util.Optional;

public interface FaqService {
    Faq create(Long projectId, String question, String answer);

    Optional<Faq> get(Long id);

    boolean delete(Long id);

    void changeQuestion(Long id, String newQuestion);

    void changeAnswer(Long id, String newAnswer);

    void update(Long id, Faq updatedFaq);
}
