package com.bloomberg.warehouse.model.enums;

import lombok.Getter;

@Getter
public enum Currency {
    USD("USD", "United States Dollar", "$"),
    EUR("EUR", "Euro", "€"),
    GBP("GBP", "British Pound Sterling", "£"),
    JPY("JPY", "Japanese Yen", "¥"),
    AUD("AUD", "Australian Dollar", "A$"),
    CAD("CAD", "Canadian Dollar", "C$");

    private final String code;
    private final String name;
    private final String symbol;

    Currency(String code, String name, String symbol) {
        this.code = code;
        this.name = name;
        this.symbol = symbol;
    }

}