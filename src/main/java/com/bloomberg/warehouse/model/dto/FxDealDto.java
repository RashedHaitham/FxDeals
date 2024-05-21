package com.bloomberg.warehouse.model.dto;

import com.neovisionaries.i18n.CurrencyCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FxDealDto {

    private CurrencyCode fromCurrency;
    private CurrencyCode toCurrency;
    private BigDecimal dealAmount;
}
