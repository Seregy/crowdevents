package com.crowdevents.project;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ProjectType {
    @JsonProperty("in_creation")
    IN_CREATION,

    @JsonProperty("active")
    ACTIVE
}
