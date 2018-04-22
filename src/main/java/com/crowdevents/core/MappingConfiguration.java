package com.crowdevents.core;

import com.crowdevents.location.LocationResource;
import com.crowdevents.project.ProjectResource;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;

import org.joda.money.Money;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MappingConfiguration {
    /**
     * Creates ModelMapper instance for mapping objects.
     *
     * @return model mapper
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        addProjectMaps(mapper);
        return mapper;
    }

    /**
     * Adds mapping configurations for mapping project and
     * related to it classes.
     *
     * @param mapper model mapper instance
     */
    public void addProjectMaps(ModelMapper mapper) {
        mapper.createTypeMap(LinkedHashMap.class, ProjectResource.class)
                .setProvider(request -> new ProjectResource())
                .setConverter(context -> {
                    LinkedHashMap source = context.getSource();
                    ProjectResource result = context.getDestination();
                    if (source.containsKey("name")) {
                        result.setName((String) source.get("name"));
                    } else if (source.containsKey("description")) {
                        result.setDescription((String) source.get("description"));
                    } else if (source.containsKey("location")) {
                        Object value = source.get("location");
                        LocationResource location = (value == null) ? null
                                : mapper.map(value, LocationResource.class);
                        result.setLocation(location);
                    } else if (source.containsKey("starts")) {
                        Object value = source.get("starts");
                        LocalDateTime dateTime = (value == null) ? null
                                : mapper.map(value, LocalDateTime.class);
                        result.setStartDateTime(dateTime);
                    } else if (source.containsKey("ends")) {
                        Object value = source.get("ends");
                        LocalDateTime dateTime = (value == null) ? null
                                : mapper.map(value, LocalDateTime.class);
                        result.setEndDateTime(dateTime);
                    } else if (source.containsKey("funding_goal")) {
                        Object value = source.get("funding_goal");
                        Money money = value == null ? null : mapper.map(value, Money.class);
                        result.setFundingGoal(money);
                    }

                    return result;
                });
    }
}
