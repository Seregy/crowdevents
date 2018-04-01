package com.crowdevents.reward;

import com.crowdevents.project.Project;
import com.crowdevents.project.ProjectRepository;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
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
    public Reward create(UUID projectId, Integer limit, Money minimalContribution, String description) {
        Project project = getProject(projectId);
        Reward reward = new Reward(project, limit, minimalContribution, description);
        return rewardRepository.save(reward);
    }

    @Override
    public Optional<Reward> get(UUID id) {
        return rewardRepository.findById(id);
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
    public void changeMinimalContribution(UUID id, Money newMinimalContribution) {
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
                .orElseThrow(() -> new IllegalArgumentException("Invalid project id: " + id));
    }

    private Reward getReward(UUID id) {
        return rewardRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid reward id: " + id));
    }
}
