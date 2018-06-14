package com.crowdevents.person;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum  PersonRole {
    @JsonProperty("admin")
    ADMIN,

    @JsonProperty("user")
    USER
}
