package com.github.lucasdc.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class LocalizedBigDecimalDeserializer extends JsonDeserializer<BigDecimal> {

    private static final DecimalFormat decimalFormat;

    static {
        decimalFormat = new DecimalFormat("#,##0.00",
                new DecimalFormatSymbols(new Locale("pt", "BR")));
        decimalFormat.setParseBigDecimal(true);
    }

    @Override
    public BigDecimal deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText();
        try {
            return (BigDecimal) decimalFormat.parse(value);
        } catch (Exception e) {
            throw new IOException("Failed to parse BigDecimal from: " + value, e);
        }
    }
}
