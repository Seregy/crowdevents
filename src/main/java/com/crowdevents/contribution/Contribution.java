package com.crowdevents.contribution;

import com.crowdevents.project.Project;
import com.crowdevents.reward.Reward;
import com.crowdevents.user.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Contribution {
    @Id
    @Column(unique = true)
    private UUID id;

    @ManyToOne
    private User contributor;

    @ManyToOne
    private Project project;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private BigDecimal amount;

    @ManyToOne
    private Reward reward;

    public Contribution(User contributor, Project project, LocalDateTime dateTime, BigDecimal amount, Reward reward) {
        this.contributor = contributor;
        this.project = project;
        this.dateTime = dateTime;
        this.amount = amount;
        this.reward = reward;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getContributor() {
        return contributor;
    }

    public void setContributor(User contributor) {
        this.contributor = contributor;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Reward getReward() {
        return reward;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contribution)) {
            return false;
        }

        Contribution that = (Contribution) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
