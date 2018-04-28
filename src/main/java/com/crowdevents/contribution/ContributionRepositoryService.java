package com.crowdevents.contribution;

import com.crowdevents.person.Person;
import com.crowdevents.person.PersonRepository;
import com.crowdevents.project.Project;
import com.crowdevents.project.ProjectRepository;
import com.crowdevents.reward.Reward;
import com.crowdevents.reward.RewardRepository;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.joda.money.Money;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
@Transactional
public class ContributionRepositoryService implements ContributionService {
    private ContributionRepository contributionRepository;
    private PersonRepository personRepository;
    private ProjectRepository projectRepository;
    private RewardRepository rewardRepository;
    private Clock clock;

    /**
     * Creates new contribution service that uses repositories.
     *
     * @param contributionRepository contribution repository
     * @param personRepository person repository
     * @param projectRepository project repository
     * @param rewardRepository reward repository
     * @param clock clock for generating date and time
     */
    @Autowired
    public ContributionRepositoryService(ContributionRepository contributionRepository,
                                         PersonRepository personRepository,
                                         ProjectRepository projectRepository,
                                         RewardRepository rewardRepository,
                                         Clock clock) {
        this.contributionRepository = contributionRepository;
        this.personRepository = personRepository;
        this.projectRepository = projectRepository;
        this.rewardRepository = rewardRepository;
        this.clock = clock;
    }

    @Override
    public Contribution contribute(Long personId, Long projectId, Money money, Long rewardId) {
        Person person = personRepository
                .findById(personId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid person id: " + personId));
        Project project = projectRepository
                .findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid project id: " + projectId));
        Reward reward = rewardRepository
                .findById(rewardId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid reward id: " + rewardId));
        Contribution contribution = new Contribution(person, project,
                LocalDateTime.now(clock), money, reward);

        person.addContribution(contribution);
        project.addContribution(contribution);
        reward.addContribution(contribution);
        return contributionRepository.save(contribution);
    }

    @Override
    public Optional<Contribution> get(Long id) {
        return contributionRepository.findById(id);
    }

    @Override
    public boolean delete(Long id) {
        if (contributionRepository.existsById(id)) {
            contributionRepository.deleteById(id);
        }

        return !contributionRepository.existsById(id);
    }

    @Override
    public void changeReward(Long id, Long newRewardId) {
        Contribution contribution = contributionRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid contribution id: " + id));
        Reward reward = rewardRepository
                .findById(newRewardId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid reward id: " + newRewardId));
        contribution.setReward(reward);
        contributionRepository.save(contribution);
    }

    @Override
    public void update(Long id, Contribution updatedContribution) {
        if (updatedContribution == null) {
            throw new IllegalArgumentException("Updated contribution must not be null");
        }

        Contribution contribution = getContribution(id);
        if (updatedContribution.getReward() != null) {
            Reward newReward = getReward(updatedContribution.getReward().getId());
            contribution.setReward(newReward);
        }
        contributionRepository.save(contribution);
    }

    private Contribution getContribution(Long id) {
        return contributionRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid contribution id: " + id));
    }

    private Reward getReward(Long id) {
        return rewardRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid reward id: " + id));
    }
}
