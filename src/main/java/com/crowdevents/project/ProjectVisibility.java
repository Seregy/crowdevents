package com.crowdevents.project;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ProjectVisibility {
    @JsonProperty("private")
    PRIVATE,

    @JsonProperty("link_only")
    LINK_ONLY,

    @JsonProperty("public")
    PUBLIC
}
