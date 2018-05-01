package com.crowdevents.core;

import com.crowdevents.contribution.ContributionResource;
import com.crowdevents.location.LocationResource;
import com.crowdevents.notification.UpdateNotification;
import com.crowdevents.notification.UpdateNotificationResource;
import com.crowdevents.project.ProjectResource;
import com.crowdevents.reward.RewardResource;
import com.crowdevents.update.UpdateResource;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;

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
        addRewardMap(mapper);
        addUpdateMaps(mapper);
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
                    }

                    if (source.containsKey("description")) {
                        result.setDescription((String) source.get("description"));
                    }

                    if (source.containsKey("location")) {
                        Object value = source.get("location");
                        LocationResource location = (value == null) ? null
                                : mapper.map(value, LocationResource.class);
                        result.setLocation(location);
                    }

                    if (source.containsKey("starts")) {
                        Object value = source.get("starts");
                        LocalDateTime dateTime = (value == null) ? null
                                : mapper.map(value, LocalDateTime.class);
                        result.setStartDateTime(dateTime);
                    }

                    if (source.containsKey("ends")) {
                        Object value = source.get("ends");
                        LocalDateTime dateTime = (value == null) ? null
                                : mapper.map(value, LocalDateTime.class);
                        result.setEndDateTime(dateTime);
                    }

                    if (source.containsKey("funding_goal")) {
                        Object value = source.get("funding_goal");
                        Money money = value == null ? null : mapper.map(value, Money.class);
                        result.setFundingGoal(money);
                    }

                    return result;
                });
    }

    /**
     * Adds mapping configurations for mapping reward and
     * related to it classes.
     *
     * @param mapper model mapper instance
     */
    public void addRewardMap(ModelMapper mapper) {
        mapper.createTypeMap(LinkedHashMap.class, RewardResource.class)
                .setProvider(request -> new RewardResource())
                .setConverter(context -> {
                    LinkedHashMap source = context.getSource();
                    RewardResource result = context.getDestination();
                    if (source.containsKey("project")) {
                        ProjectResource value = (ProjectResource) source.get("project");
                        result.setProject(value);
                    }

                    if (source.containsKey("description")) {
                        String value = (String) source.get("description");
                        result.setDescription(value);
                    }

                    if (source.containsKey("contributions")) {
                        @SuppressWarnings("unchecked")
                        List<ContributionResource> value = (List) source.get("contributions");
                        result.setContributions(value);
                    }

                    if (source.containsKey("minimal_contribution")) {
                        Money value = (Money) source.get("minimal_contribution");
                        result.setMinimalContribution(value);
                    }

                    if (source.containsKey("limit")) {
                        Integer value = (Integer) source.get("limit");
                        result.setLimit(value);
                    }

                    if (source.containsKey("delivery_date")) {
                        String value = (String) source.get("delivery_date");
                        result.setDeliveryDate(value);
                    }

                    if (source.containsKey("shipping_location")) {
                        String value = (String) source.get("shipping_location");
                        result.setShippingLocation(value);
                    }

                    return result;
                });
    }

    /**
     * Adds mapping configurations for mapping update and
     * related to it classes.
     *
     * @param mapper model mapper instance
     */
    public void addUpdateMaps(ModelMapper mapper) {
        mapper.createTypeMap(LinkedHashMap.class, UpdateResource.class)
                .setProvider(request -> new UpdateResource())
                .setConverter(context -> {
                    LinkedHashMap source = context.getSource();
                    UpdateResource result = context.getDestination();
                    if (source.containsKey("project")) {
                        ProjectResource value = (ProjectResource) source.get("project");
                        result.setProject(value);
                    }

                    if (source.containsKey("posted")) {
                        LocalDateTime value = LocalDateTime.parse((String) source.get("posted"));
                        result.setDateTime(value);
                    }

                    if (source.containsKey("message")) {
                        String value = (String) source.get("message");
                        result.setMessage(value);
                    }

                    if (source.containsKey("short_message")) {
                        String value = (String) source.get("short_message");
                        result.setShortMessage(value);
                    }

                    return result;
                });
    }
}
