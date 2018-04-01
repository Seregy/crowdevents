package com.crowdevents.notification;

import com.crowdevents.contribution.Contribution;
import com.crowdevents.person.Person;
import com.crowdevents.project.Project;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ContributionNotification extends BaseNotification {
    @ManyToOne
    private Contribution contribution;

    public ContributionNotification(String message, Contribution contribution,
                                    Person receiver, LocalDateTime dateTime,
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
