package com.crowdevents.notification;

import com.crowdevents.core.web.Views;
import com.crowdevents.update.UpdateResource;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

@JsonView(Views.Detailed.class)
public class UpdateNotificationResource extends BaseNotificationResource {
    @JsonProperty("update")
    @JsonIgnoreProperties({"project"})
    @JsonView(Views.Minimal.class)
    private UpdateResource updateResource;

    public UpdateResource getUpdateResource() {
        return updateResource;
    }

    public void setUpdateResource(UpdateResource updateResource) {
        this.updateResource = updateResource;
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.UPDATE;
    }
}
