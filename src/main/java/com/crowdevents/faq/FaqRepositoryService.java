package com.crowdevents.faq;

import com.crowdevents.project.Project;
import com.crowdevents.project.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FaqRepositoryService implements FaqService {
    private FaqRepository faqRepository;
    private ProjectRepository projectRepository;

    @Autowired
    public FaqRepositoryService(FaqRepository faqRepository, ProjectRepository projectRepository) {
        this.faqRepository = faqRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public Faq create(UUID projectId, String question, String answer) {
        Project project = projectRepository
                .findById(projectId)
                .orElseThrow(() -> new RuntimeException("Couldn't find project with id: " + projectId));
        Faq faq = new Faq(project, question, answer);

        project.getFaqs().add(faq);
        return faqRepository.save(faq);
    }

    @Override
    public Faq get(UUID id) {
        return faqRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Couldn't find faq with id: " + id));
    }

    @Override
    public void delete(UUID id) {
        faqRepository.deleteById(id);
    }

    @Override
    public void changeQuestion(UUID id, String newQuestion) {
        Faq faq = faqRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Couldn't find faq with id: " + id));
        faq.setQuestion(newQuestion);
        faqRepository.save(faq);
    }

    @Override
    public void changeAnswer(UUID id, String newAnswer) {
        Faq faq = faqRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Couldn't find faq with id: " + id));
        faq.setAnswer(newAnswer);
        faqRepository.save(faq);
    }
}
