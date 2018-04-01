package com.crowdevents.reward;

import com.crowdevents.contribution.Contribution;
import com.crowdevents.project.Project;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.joda.money.Money;

import javax.persistence.*;
import java.util.HashSet;
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
    private Set<Contribution> contributions = new HashSet<>();

    @Columns(columns = { @Column(name = "funding_goal_currency"),
            @Column(name = "funding_goal_amount", nullable = false) })
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmountAndCurrency")
    private Money minimalContribution;

    private Integer maximumAmount;

    @Column(nullable = false)
    private String description;

    private String deliveryDate;
    private String shippedTo;

    public Reward(Project project, Integer limit, Money minimalContribution, String description) {
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

    public void addContribution(Contribution contribution) {
        contributions.add(contribution);
        contribution.setReward(this);
    }

    public void removeContribution(Contribution contribution) {
        contributions.remove(contribution);
        contribution.setReward(null);
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

    public Money getMinimalContribution() {
        return minimalContribution;
    }

    public void setMinimalContribution(Money minimalContribution) {
        this.minimalContribution = minimalContribution;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getShippedTo() {
        return shippedTo;
    }

    public void setShippedTo(String shippedTo) {
        this.shippedTo = shippedTo;
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
