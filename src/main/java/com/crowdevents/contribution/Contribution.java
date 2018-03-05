package com.crowdevents.contribution;

import com.crowdevents.project.Project;
import com.crowdevents.reward.Reward;
import com.crowdevents.person.Person;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.joda.money.Money;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Contribution {
    @Id
    @Column(unique = true)
    private UUID id;

    @ManyToOne
    private Person contributor;

    @ManyToOne
    private Project project;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Columns(columns = {
            @Column(name = "money_currency", nullable = false),
            @Column(name = "money_amount", nullable = false)
    })
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmountAndCurrency")
    private Money money;

    @ManyToOne
    private Reward reward;

    public Contribution(Person contributor, Project project, LocalDateTime dateTime, Money money, Reward reward) {
        this.contributor = contributor;
        this.project = project;
        this.dateTime = dateTime;
        this.money = money;
        this.reward = reward;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Person getContributor() {
        return contributor;
    }

    public void setContributor(Person contributor) {
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

    public Money getMoney() {
        return money;
    }

    public void setMoney(Money money) {
        this.money = money;
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
