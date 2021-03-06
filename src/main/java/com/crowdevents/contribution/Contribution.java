package com.crowdevents.contribution;

import com.crowdevents.person.Person;
import com.crowdevents.project.Project;
import com.crowdevents.reward.Reward;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.joda.money.BigMoney;
import org.joda.money.Money;

@Entity
public class Contribution {
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Person contributor;

    @ManyToOne
    private Project project;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Columns(columns = {
            @Column(name = "money_currency", nullable = false, length = 3),
            @Column(name = "money_amount", nullable = false)
    })
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentBigMoneyAmountAndCurrency")
    private BigMoney money;

    @ManyToOne
    private Reward reward;

    @Column(name = "payment_id")
    private String paymentId;

    /**
     * Constructs new contribution to the project.
     *
     * @param contributor person who made the contribution
     * @param project project to which the contribution was made
     * @param dateTime creation date and time
     * @param money contributed currency and amount
     * @param reward reward for this contribution
     */
    public Contribution(Person contributor, Project project, LocalDateTime dateTime,
                        Money money, Reward reward, String paymentId) {
        this.contributor = contributor;
        this.project = project;
        this.dateTime = dateTime;
        this.money = money.toBigMoney();
        this.reward = reward;
        this.paymentId = paymentId;
    }

    protected Contribution() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        return money.toMoney();
    }

    public void setMoney(Money money) {
        this.money = money.toBigMoney();
    }

    public Reward getReward() {
        return reward;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
}
