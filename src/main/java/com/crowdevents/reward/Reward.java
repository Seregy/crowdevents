package com.crowdevents.reward;

import com.crowdevents.contribution.Contribution;
import com.crowdevents.project.Project;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
public class Reward {
    @Id
    @Column(unique = true)
    private UUID id;

    @ManyToOne
    private Project project;

    @OneToMany(mappedBy = "reward")
    private Set<Contribution> contributions;

    @Column(nullable = false)
    private Integer minimalContribution;

    private Integer maximumAmount;

    @Column(nullable = false)
    private String description;

    public Reward(Project project, Integer limit, Integer minimalContribution, String description) {
        this.project = project;
        this.maximumAmount = limit;
        this.minimalContribution = minimalContribution;
        this.description = description;
        this.id = UUID.randomUUID();
    }

    protected Reward() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Set<Contribution> getContributions() {
        return contributions;
    }

    public void setContributions(Set<Contribution> contributions) {
        this.contributions = contributions;
    }

    public Integer getMaximumAmount() {
        return maximumAmount;
    }

    public void setMaximumAmount(Integer maximumAmount) {
        this.maximumAmount = maximumAmount;
    }

    public Integer getMinimalContribution() {
        return minimalContribution;
    }

    public void setMinimalContribution(Integer minimalContribution) {
        this.minimalContribution = minimalContribution;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reward)) {
            return false;
        }

        Reward reward = (Reward) o;
        return Objects.equals(id, reward.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
