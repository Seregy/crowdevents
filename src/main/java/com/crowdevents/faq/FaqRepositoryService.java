package com.crowdevents.faq;

import com.crowdevents.project.Project;
import com.crowdevents.project.ProjectRepository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public Faq create(Long projectId, String question, String answer) {
        Project project = projectRepository
                .findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid project id: " + projectId));
        Faq faq = new Faq(project, question, answer);

        project.addFaq(faq);
        return faqRepository.save(faq);
    }

    @Override
    public Optional<Faq> get(Long id) {
        return faqRepository.findById(id);
    }

    @Override
    public Page<Faq> getAll(Pageable pageable) {
        return faqRepository.findAll(pageable);
    }

    @Override
    public Page<Faq> getAllByProject(Long projectId, Pageable pageable) {
        return faqRepository.findAllByProjectId(projectId, pageable);
    }

    @Override
    public boolean delete(Long id) {
        if (faqRepository.existsById(id)) {
            faqRepository.deleteById(id);
        }

        return !faqRepository.existsById(id);
    }

    @Override
    public void update(Long id, Faq updatedFaq) {
        if (updatedFaq == null) {
            throw new IllegalArgumentException("Updated faq must not be null");
        }

        Faq faq = getFaq(id);
        faq.setQuestion(updatedFaq.getQuestion());
        faq.setAnswer(updatedFaq.getAnswer());
        faqRepository.save(faq);
    }

    private Faq getFaq(Long id) {
        return faqRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid faq id: " + id));
    }
}
