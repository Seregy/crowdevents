package com.crowdevents.core;

import com.crowdevents.util.JodaMoneyDeserializer;
import com.crowdevents.util.JodaMoneySerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.joda.money.Money;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfiguration {
    @Bean
    public Module jodaMoneyModule() {
        return new SimpleModule("Joda money")
                .addSerializer(Money.class, new JodaMoneySerializer(Money.class))
                .addDeserializer(Money.class, new JodaMoneyDeserializer(Money.class));
    }
}
