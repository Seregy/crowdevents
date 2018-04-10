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

import java.util.UUID;

@Configuration
public class MappingConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        addPersonUUIDTypeMap(mapper);
        addContributionUUIDTypeMap(mapper);
        addCommentUUIDTypeMap(mapper);
        addFaqUUIDTypeMap(mapper);
        addCategoryUUIDTypeMap(mapper);
        addUpdateUUIDTypeMap(mapper);
        addRewardUUIDTypeMap(mapper);
        return mapper;
    }

    public void addPersonUUIDTypeMap(ModelMapper mapper) {
        mapper.createTypeMap(Person.class, UUID.class)
                .setProvider(request -> UUID.fromString("00000000-0000-0000-0000-000000000000"))
                .setConverter(context -> context.getSource().getId());
    }

    public void addContributionUUIDTypeMap(ModelMapper mapper) {
        mapper.createTypeMap(Contribution.class, UUID.class)
                .setProvider(request -> UUID.fromString("00000000-0000-0000-0000-000000000000"))
                .setConverter(context -> context.getSource().getId());
    }

    public void addCommentUUIDTypeMap(ModelMapper mapper) {
        mapper.createTypeMap(Comment.class, UUID.class)
                .setProvider(request -> UUID.fromString("00000000-0000-0000-0000-000000000000"))
                .setConverter(context -> context.getSource().getId());
    }

    public void addFaqUUIDTypeMap(ModelMapper mapper) {
        mapper.createTypeMap(Faq.class, UUID.class)
                .setProvider(request -> UUID.fromString("00000000-0000-0000-0000-000000000000"))
                .setConverter(context -> context.getSource().getId());
    }

    public void addCategoryUUIDTypeMap(ModelMapper mapper) {
        mapper.createTypeMap(Category.class, UUID.class)
                .setProvider(request -> UUID.fromString("00000000-0000-0000-0000-000000000000"))
                .setConverter(context -> context.getSource().getId());
    }

    public void addUpdateUUIDTypeMap(ModelMapper mapper) {
        mapper.createTypeMap(Update.class, UUID.class)
                .setProvider(request -> UUID.fromString("00000000-0000-0000-0000-000000000000"))
                .setConverter(context -> context.getSource().getId());
    }

    public void addRewardUUIDTypeMap(ModelMapper mapper) {
        mapper.createTypeMap(Reward.class, UUID.class)
                .setProvider(request -> UUID.fromString("00000000-0000-0000-0000-000000000000"))
                .setConverter(context -> context.getSource().getId());
    }
}
