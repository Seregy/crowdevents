package com.crowdevents.contribution;

import com.crowdevents.person.Person;
import com.crowdevents.person.PersonRepository;
import com.crowdevents.project.Project;
import com.crowdevents.project.ProjectRepository;
import com.crowdevents.reward.Reward;
import com.crowdevents.reward.RewardRepository;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ContributionRepositoryService implements ContributionService {
    private ContributionRepository contributionRepository;
    private PersonRepository personRepository;
    private ProjectRepository projectRepository;
    private RewardRepository rewardRepository;
    private Clock clock;

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
    public Contribution contribute(UUID personId, UUID projectId, Money money, UUID rewardId) {
        Person person = personRepository
                .findById(personId)
                .orElseThrow(() -> new RuntimeException("Couldn't find person with id: " + personId));
        Project project = projectRepository
                .findById(projectId)
                .orElseThrow(() -> new RuntimeException("Couldn't find project with id: " + projectId));
        Reward reward = rewardRepository
                .findById(rewardId)
                .orElseThrow(() -> new RuntimeException("Couldn't find reward with id: " + rewardId));
        Contribution contribution = new Contribution(person, project, LocalDateTime.now(clock), money, reward);

        person.getContributions().add(contribution);
        project.getContributions().add(contribution);
        reward.getContributions().add(contribution);
        return contributionRepository.save(contribution);
    }

    @Override
    public Optional<Contribution> get(UUID id) {
        return contributionRepository.findById(id);
    }

    @Override
    public void delete(UUID id) {
        contributionRepository.deleteById(id);
    }

    @Override
    public void changeReward(UUID id, UUID newRewardId) {
        Contribution contribution = contributionRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Couldn't find contribution with id: " + id));
        Reward reward = rewardRepository
                .findById(newRewardId)
                .orElseThrow(() -> new RuntimeException("Couldn't find reward with id: " + newRewardId));
        contribution.setReward(reward);
        contributionRepository.save(contribution);
    }
}
