package com.crowdevents.core;

import com.crowdevents.category.Category;
import com.crowdevents.comment.Comment;
import com.crowdevents.contribution.Contribution;
import com.crowdevents.faq.Faq;
import com.crowdevents.location.Location;
import com.crowdevents.person.Person;
import com.crowdevents.project.ProjectResource;
import com.crowdevents.reward.Reward;
import com.crowdevents.update.Update;
import org.joda.money.Money;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;

@Configuration
public class MappingConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        addProjectMaps(mapper);
        addPersonLongTypeMap(mapper);
        addContributionLongTypeMap(mapper);
        addCommentLongTypeMap(mapper);
        addFaqLongTypeMap(mapper);
        addCategoryLongTypeMap(mapper);
        addUpdateLongTypeMap(mapper);
        addRewardLongTypeMap(mapper);
        return mapper;
    }

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
                        Location location = value == null ? null : mapper.map(value, Location.class);
                        result.setLocation(location);
                    } else if (source.containsKey("start_date_time")) {
                        Object value = source.get("start_date_time");
                        LocalDateTime dateTime = value == null ? null : mapper.map(value, LocalDateTime.class);
                        result.setStartDateTime(dateTime);
                    } else if (source.containsKey("end_date_time")) {
                        Object value = source.get("end_date_time");
                        LocalDateTime dateTime = value == null ? null : mapper.map(value, LocalDateTime.class);
                        result.setEndDateTime(dateTime);
                    } else if (source.containsKey("funding_goal")) {
                        Object value = source.get("funding_goal");
                        Money money = value == null ? null : mapper.map(value, Money.class);
                        result.setFundingGoal(money);
                    }

                    return result;
                });
    }

    public void addPersonLongTypeMap(ModelMapper mapper) {
        mapper.createTypeMap(Person.class, Long.class)
                .setProvider(request -> -1L)
                .setConverter(context -> context.getSource().getId());
    }

    public void addContributionLongTypeMap(ModelMapper mapper) {
        mapper.createTypeMap(Contribution.class, Long.class)
                .setProvider(request -> -1L)
                .setConverter(context -> context.getSource().getId());
    }

    public void addCommentLongTypeMap(ModelMapper mapper) {
        mapper.createTypeMap(Comment.class, Long.class)
                .setProvider(request -> -1L)
                .setConverter(context -> context.getSource().getId());
    }

    public void addFaqLongTypeMap(ModelMapper mapper) {
        mapper.createTypeMap(Faq.class, Long.class)
                .setProvider(request -> -1L)
                .setConverter(context -> context.getSource().getId());
    }

    public void addCategoryLongTypeMap(ModelMapper mapper) {
        mapper.createTypeMap(Category.class, Long.class)
                .setProvider(request -> -1L)
                .setConverter(context -> context.getSource().getId());
    }

    public void addUpdateLongTypeMap(ModelMapper mapper) {
        mapper.createTypeMap(Update.class, Long.class)
                .setProvider(request -> -1L)
                .setConverter(context -> context.getSource().getId());
    }

    public void addRewardLongTypeMap(ModelMapper mapper) {
        mapper.createTypeMap(Reward.class, Long.class)
                .setProvider(request -> -1L)
                .setConverter(context -> context.getSource().getId());
    }
}
