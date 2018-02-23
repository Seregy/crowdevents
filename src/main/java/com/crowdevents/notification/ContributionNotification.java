package com.crowdevents.notification;

import com.crowdevents.contribution.Contribution;
import com.crowdevents.project.Project;
import com.crowdevents.person.Person;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class ContributionNotification extends BaseNotification {
    @ManyToOne
    private Contribution contribution;

    public ContributionNotification(String message, Contribution contribution, Person receiver, LocalDateTime dateTime,
                                    Project project) {
        super(message, receiver, dateTime, project);
        this.contribution = contribution;
    }

    protected ContributionNotification() {
        super();
    }

    public Contribution getContribution() {
        return contribution;
    }

    public void setContribution(Contribution contribution) {
        this.contribution = contribution;
    }
}
