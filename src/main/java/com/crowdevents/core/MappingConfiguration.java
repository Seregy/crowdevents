package com.crowdevents.core;

import com.crowdevents.category.Category;
import com.crowdevents.comment.Comment;
import com.crowdevents.contribution.Contribution;
import com.crowdevents.faq.Faq;
import com.crowdevents.person.Person;
import com.crowdevents.reward.Reward;
import com.crowdevents.update.Update;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MappingConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        addPersonLongTypeMap(mapper);
        addContributionLongTypeMap(mapper);
        addCommentLongTypeMap(mapper);
        addFaqLongTypeMap(mapper);
        addCategoryLongTypeMap(mapper);
        addUpdateLongTypeMap(mapper);
        addRewardLongTypeMap(mapper);
        return mapper;
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
