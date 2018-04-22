package com.crowdevents.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

import org.joda.money.Money;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class JodaMoneySerializer extends StdSerializer<Money> {
    public JodaMoneySerializer() {
        super(Money.class);
    }

    @Override
    public void serialize(Money value, JsonGenerator gen,
                          SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("currency", value.getCurrencyUnit().getCurrencyCode());
        gen.writeNumberField("amount", value.getAmount());
        gen.writeEndObject();
    }
}
