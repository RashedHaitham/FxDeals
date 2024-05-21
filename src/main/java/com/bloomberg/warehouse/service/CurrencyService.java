package com.bloomberg.warehouse.service;

import com.neovisionaries.i18n.CurrencyCode;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyService {

    public List<CurrencyCode> getAllCurrencies() {
        return Arrays.stream(CurrencyCode.values())
                .skip(1)  // Skip the first element
                .collect(Collectors.toList());
    }
}
