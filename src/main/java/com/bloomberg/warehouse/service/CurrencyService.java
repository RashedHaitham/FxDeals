package com.bloomberg.warehouse.service;

import com.bloomberg.warehouse.model.enums.Currency;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CurrencyService {

    public List<Currency> getAllCurrencies() {
        return Arrays.stream(Currency.values())
                .collect(Collectors.toList());
    }

    public Map<String, String> getCurrencySymbols() {
        Map<String, String> currencySymbols = new HashMap<>();
        for (Currency currency : Currency.values()) {
            currencySymbols.put(currency.getCode(), currency.getSymbol());
        }
        return currencySymbols;
    }
}
