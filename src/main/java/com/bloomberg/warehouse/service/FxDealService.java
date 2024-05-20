package com.bloomberg.warehouse.service;

import com.bloomberg.warehouse.exceptions.CurrencyNotFoundException;
import com.bloomberg.warehouse.exceptions.DuplicateDealException;
import com.bloomberg.warehouse.exceptions.SameCurrencyException;
import com.bloomberg.warehouse.model.dto.FxDealDto;
import com.bloomberg.warehouse.model.entity.FxDeal;
import com.bloomberg.warehouse.model.enums.Currency;
import com.bloomberg.warehouse.repository.FxDealDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FxDealService {
    private final FxDealDAO fxDealDAO;

    @Autowired
    public FxDealService(FxDealDAO fxDealDAO) {
        this.fxDealDAO = fxDealDAO;
    }

    public FxDealDto addFxDeal(FxDealDto fxDealDto) {
        try {
            if (fxDealDto.getFromCurrency().equals(fxDealDto.getToCurrency())) {
                String errorMessage = "From Currency and To Currency cannot be the same.";
                log.error(errorMessage);
                throw new SameCurrencyException(errorMessage);
            }

            if (fxDealDAO.getFxDeal(fxDealDto.getId()).isPresent()) {
                String errorMessage = "A deal already exists.";
                log.error(errorMessage);
                throw new DuplicateDealException(errorMessage);
            }

            if (isValidCurrency(fxDealDto.getFromCurrency()) || isValidCurrency(fxDealDto.getToCurrency())) {
                throw new CurrencyNotFoundException("Invalid currency provided");
            }
            FxDeal fxDeal = mapToFxDeal(fxDealDto);
            fxDealDAO.insertFxDeal(fxDeal);
            log.info("FX Deal saved successfully: {}", fxDealDto);

            return fxDealDto;
        } catch (DuplicateDealException | SameCurrencyException | CurrencyNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            String errorMessage = "An error occurred while importing the deal. Please try again later.";
            log.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
    }

    private boolean isValidCurrency(Currency currency) {
        for (Currency validCurrency : Currency.values()) {
            if (validCurrency == currency) {
                return false;
            }
        }
        return true;
    }

    private FxDeal mapToFxDeal(FxDealDto fxDealDto) {
        return FxDeal.builder()
                .id(fxDealDto.getId())
                .dealAmount(fxDealDto.getDealAmount())
                .dealTimestamp(fxDealDto.getDealTimestamp())
                .fromCurrency(fxDealDto.getFromCurrency().name())
                .toCurrency(fxDealDto.getToCurrency().name())
                .build();
    }
}
