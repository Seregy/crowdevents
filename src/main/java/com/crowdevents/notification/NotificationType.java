package com.crowdevents.notification;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum NotificationType {
    @JsonProperty("base")
    BASE,

    @JsonProperty("contribution")
    CONTRIBUTION,

    @JsonProperty("person")
    PERSON,

    @JsonProperty("update")
    UPDATE
}
