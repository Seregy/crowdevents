package com.crowdevents.faq;

import java.util.UUID;

public interface FaqService {
    Faq create(UUID projectId, String question, String answer);
    Faq get(UUID id);
    void delete(UUID id);
    void changeQuestion(UUID id, String newQuestion);
    void changeAnswer(UUID id, String newAnswer);
}
