package com.crowdevents.reward;

import com.crowdevents.project.Project;
import com.crowdevents.project.ProjectRepository;

import java.util.Optional;

import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RewardRepositoryService implements RewardService {
    private RewardRepository rewardRepository;
    private ProjectRepository projectRepository;

    @Autowired
    public RewardRepositoryService(RewardRepository rewardRepository,
                                   ProjectRepository projectRepository) {
        this.rewardRepository = rewardRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public Reward create(Long projectId, Integer limit, Money minimalContribution,
                         String description) {
        Project project = getProject(projectId);
        Reward reward = new Reward(project, limit, minimalContribution, description);
        return rewardRepository.save(reward);
    }

    @Override
    public Optional<Reward> get(Long id) {
        return rewardRepository.findById(id);
    }

    @Override
    public Page<Reward> getAll(Pageable pageable) {
        return rewardRepository.findAll(pageable);
    }

    @Override
    public Page<Reward> getAllByProject(Long projectId, Pageable pageable) {
        return rewardRepository.findAllByProjectId(projectId, pageable);
    }

    @Override
    public boolean delete(Long id) {
        if (rewardRepository.existsById(id)) {
            rewardRepository.deleteById(id);
        }

        return !rewardRepository.existsById(id);
    }

    @Override
    public void update(Long id, Reward updatedReward) {
        if (updatedReward == null) {
            throw new IllegalArgumentException("Updated reward must not be null");
        }

        Reward reward = getReward(id);
        reward.setLimit(updatedReward.getLimit());
        reward.setMinimalContribution(updatedReward.getMinimalContribution());
        reward.setDescription(updatedReward.getDescription());
        reward.setDeliveryDate(updatedReward.getDeliveryDate());
        reward.setShippingLocation(updatedReward.getShippingLocation());
        rewardRepository.save(reward);
    }

    private Project getProject(Long id) {
        return projectRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid project id: " + id));
    }

    private Reward getReward(Long id) {
        return rewardRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid reward id: " + id));
    }
}
