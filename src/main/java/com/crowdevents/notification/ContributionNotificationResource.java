package com.crowdevents.notification;

import com.crowdevents.contribution.Contribution;
import com.crowdevents.contribution.ContributionResource;
import com.crowdevents.core.web.Views;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

@JsonView(Views.Detailed.class)
public class ContributionNotificationResource extends BaseNotificationResource {
    @JsonIgnoreProperties({"project", "reward"})
    @JsonView(Views.Minimal.class)
    private ContributionResource contribution;

    public ContributionResource getContribution() {
        return contribution;
    }

    public void setContribution(ContributionResource contribution) {
        this.contribution = contribution;
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.CONTRIBUTION;
    }
}
