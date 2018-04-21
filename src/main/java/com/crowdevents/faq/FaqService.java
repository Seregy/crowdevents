package com.crowdevents.faq;

import java.util.Optional;

public interface FaqService {
    Faq create(Long projectId, String question, String answer);

    Optional<Faq> get(Long id);

    void delete(Long id);

    void changeQuestion(Long id, String newQuestion);

    void changeAnswer(Long id, String newAnswer);
}
