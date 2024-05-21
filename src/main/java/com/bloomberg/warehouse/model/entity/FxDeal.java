package com.bloomberg.warehouse.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FxDeal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "From Currency cannot be null")
    private String fromCurrency;

    @NotNull(message = "To Currency cannot be null")
    private String toCurrency;

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    private LocalDateTime dealTimestamp;

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "1.0", message = "Deal amount can't be negative or less than 1")
    private BigDecimal dealAmount;

    @PrePersist
    public void prePersist() {
        if (this.dealTimestamp == null) {
            this.dealTimestamp = LocalDateTime.now();
        }
    }
}
