package com.crowdevents.faq;

import com.crowdevents.project.Project;
import com.crowdevents.project.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
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
                .orElseThrow(() -> new IllegalArgumentException("Invalid project id: " + projectId));
        Faq faq = new Faq(project, question, answer);

        project.addFaq(faq);
        return faqRepository.save(faq);
    }

    @Override
    public Optional<Faq> get(UUID id) {
        return faqRepository.findById(id);
    }

    @Override
    public void delete(UUID id) {
        faqRepository.deleteById(id);
    }

    @Override
    public void changeQuestion(UUID id, String newQuestion) {
        Faq faq = faqRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid faq id: " + id));
        faq.setQuestion(newQuestion);
        faqRepository.save(faq);
    }

    @Override
    public void changeAnswer(UUID id, String newAnswer) {
        Faq faq = faqRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid faq id: " + id));
        faq.setAnswer(newAnswer);
        faqRepository.save(faq);
    }
}
