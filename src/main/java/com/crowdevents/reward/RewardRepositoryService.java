package com.crowdevents.reward;

import com.crowdevents.project.Project;
import com.crowdevents.project.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RewardRepositoryService implements RewardService {
    private RewardRepository rewardRepository;
    private ProjectRepository projectRepository;

    @Autowired
    public RewardRepositoryService(RewardRepository rewardRepository, ProjectRepository projectRepository) {
        this.rewardRepository = rewardRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public Reward create(UUID projectId, Integer limit, Integer minimalContribution, String description) {
        Project project = getProject(projectId);
        Reward reward = new Reward(project, limit, minimalContribution, description);
        return rewardRepository.save(reward);
    }

    @Override
    public Reward get(UUID id) {
        return getReward(id);
    }

    @Override
    public void delete(UUID id) {
        rewardRepository.deleteById(id);
    }

    @Override
    public void changeLimit(UUID id, Integer newLimit) {
        Reward reward = getReward(id);
        reward.setMaximumAmount(newLimit);
        rewardRepository.save(reward);
    }

    @Override
    public void changeMinimalContribution(UUID id, Integer newMinimalContribution) {
        Reward reward = getReward(id);
        reward.setMinimalContribution(newMinimalContribution);
        rewardRepository.save(reward);
    }

    @Override
    public void changeDescription(UUID id, String newDescription) {
        Reward reward = getReward(id);
        reward.setDescription(newDescription);
        rewardRepository.save(reward);
    }

    @Override
    public void changeDeliveryDate(UUID id, String newDeliveryDate) {
        Reward reward = getReward(id);
        reward.setDeliveryDate(newDeliveryDate);
        rewardRepository.save(reward);
    }

    @Override
    public void changeShippingLocation(UUID id, String newShippingLocation) {
        Reward reward = getReward(id);
        reward.setShippedTo(newShippingLocation);
        rewardRepository.save(reward);
    }

    private Project getProject(UUID id) {
        return projectRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Couldn't find project with id: " + id));
    }

    private Reward getReward(UUID id) {
        return rewardRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Couldn't find reward with id: " + id));
    }
}
