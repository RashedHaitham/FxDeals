package com.bloomberg.warehouse.model.dto;

import com.bloomberg.warehouse.model.enums.Currency;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FxDealDto {

    @Min(value = 1, message = "Id can't be negative")
    private int id;
    @NotNull(message = "From Currency cannot be null")
    @Enumerated(EnumType.STRING)
    private Currency fromCurrency;
    @NotNull(message = "To Currency cannot be null")
    @Enumerated(EnumType.STRING)
    private Currency toCurrency;
    @PastOrPresent(message = "Deal timestamp must be in the past or present")
    @NotNull(message = "Date cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dealTimestamp;
    @DecimalMin(value = "0.0", message = "Deal amount can't be negative")
    @NotNull(message = "Deal Amount cannot be null")
    private BigDecimal dealAmount;


}
