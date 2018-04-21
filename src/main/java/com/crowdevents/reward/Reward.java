package com.crowdevents.reward;

import com.crowdevents.contribution.Contribution;
import com.crowdevents.project.Project;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.joda.money.Money;

@Entity
public class Reward {
    @Id
    @Column(unique = true)
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Project project;

    @OneToMany(mappedBy = "reward")
    private Set<Contribution> contributions = new HashSet<>();

    @Columns(columns = { @Column(name = "funding_goal_currency", nullable = false),
            @Column(name = "funding_goal_amount", nullable = false) })
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmountAndCurrency")
    private Money minimalContribution;

    private Integer maximumAmount;

    @Column(nullable = false)
    private String description;

    private String deliveryDate;
    private String shippedTo;

    /**
     * Constructs new reward for contribution specified amount of money.
     *
     * @param project project to which reward belongs
     * @param limit amount of users that can claim this reward
     * @param minimalContribution minimal contribution needed for claiming this reward
     * @param description description of the reward
     */
    public Reward(Project project, Integer limit, Money minimalContribution, String description) {
        this.project = project;
        this.maximumAmount = limit;
        this.minimalContribution = minimalContribution;
        this.description = description;
    }

    protected Reward() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
