package com.bloomberg.warehouse.service;

import com.bloomberg.warehouse.exceptions.CurrencyNotFoundException;
import com.bloomberg.warehouse.exceptions.DuplicateDealException;
import com.bloomberg.warehouse.exceptions.SameCurrencyException;
import com.bloomberg.warehouse.model.dto.FxDealDto;
import com.bloomberg.warehouse.model.entity.FxDeal;
import com.bloomberg.warehouse.repository.FxDealDAO;
import com.neovisionaries.i18n.CurrencyCode;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
public class FxDealService {
    private final FxDealDAO fxDealDAO;

    @Autowired
    public FxDealService(FxDealDAO fxDealDAO) {
        this.fxDealDAO = fxDealDAO;
    }

    public FxDeal addFxDeal(FxDealDto fxDealDto) {
        try {
            FxDeal fxDeal = mapToFxDeal(fxDealDto);

            if (fxDeal.getFromCurrency().equals(fxDeal.getToCurrency())) {
                String errorMessage = "From Currency and To Currency cannot be the same.";
                log.error(errorMessage);
                throw new SameCurrencyException(errorMessage);
            }

            if (fxDealDAO.getFxDeal(fxDeal.getId()).isPresent()) {
                String errorMessage = "A deal already exists.";
                log.error(errorMessage);
                throw new DuplicateDealException(errorMessage);
            }

            if (!isValidCurrency(CurrencyCode.valueOf(fxDeal.getFromCurrency())) || !isValidCurrency(CurrencyCode.valueOf(fxDeal.getToCurrency()))) {
                throw new CurrencyNotFoundException("Invalid currency provided");
            }

            fxDealDAO.insertFxDeal(fxDeal);
            log.info("FX Deal saved successfully: {}", fxDeal);

            return fxDeal;
        } catch (DuplicateDealException | SameCurrencyException | CurrencyNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        catch (ConstraintViolationException e) {

            for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
                System.out.println("Validation error: " + violation.getMessage());
            }
            throw e;
        }
        catch (Exception e) {
            String errorMessage = "An error occurred while importing the deal. Please try again later.";
            log.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
    }

    private boolean isValidCurrency(CurrencyCode currency) {
        return Arrays.stream(CurrencyCode.values())
                .skip(1)  // Skip the first element
                .anyMatch(validCurrency -> validCurrency == currency);
    }

    private FxDeal mapToFxDeal(FxDealDto fxDealDto) {
        return FxDeal.builder()
                .dealAmount(fxDealDto.getDealAmount())
                .fromCurrency(fxDealDto.getFromCurrency().name())
                .toCurrency(fxDealDto.getToCurrency().name())
                .build();
    }
}
