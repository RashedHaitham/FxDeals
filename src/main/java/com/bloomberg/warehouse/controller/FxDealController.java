package com.bloomberg.warehouse.controller;

import com.bloomberg.warehouse.exceptions.CurrencyNotFoundException;
import com.bloomberg.warehouse.exceptions.DuplicateDealException;
import com.bloomberg.warehouse.exceptions.SameCurrencyException;
import com.bloomberg.warehouse.model.dto.FxDealDto;
import com.bloomberg.warehouse.model.entity.FxDeal;
import com.bloomberg.warehouse.service.CurrencyService;
import com.bloomberg.warehouse.service.FxDealService;
import com.neovisionaries.i18n.CurrencyCode;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/api/deals")
@Slf4j
public class FxDealController {

    private final FxDealService fxDealService;
    private final CurrencyService currencyService;

    @Autowired
    public FxDealController(FxDealService fxDealService, CurrencyService currencyService) {
        this.fxDealService = fxDealService;
        this.currencyService = currencyService;
    }

    @GetMapping
    public String showAddFxDealForm(Model model) {
        populateModelAttributes(model);
        return "add-fx-deal";
    }

    @PostMapping("/deal")
    public String createFXDeal( @Validated FxDealDto dealDTO, Model model) {
        log.info("Received request to create FX deal: From Currency: {}, To Currency: {}, Deal Amount: {}",
                dealDTO.getFromCurrency(), dealDTO.getToCurrency(), dealDTO.getDealAmount());

        try {
            FxDeal createdDeal = fxDealService.addFxDeal(dealDTO);
            log.info("FX deal created successfully: {}", createdDeal);
            model.addAttribute("successMessage", "FX deal created successfully");
        } catch (SameCurrencyException | DuplicateDealException e) {
            log.error("Failed to create FX deal: {}", e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
        } catch (CurrencyNotFoundException e) {
            log.error("Currency not found: {}", e.getMessage());
            model.addAttribute("errorMessage", "Currency not found: " + e.getMessage());
        }
        catch (ConstraintViolationException e){
            StringBuilder messages= new StringBuilder();
            for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
                messages.append(violation.getMessage());
            }
            String errorMessage = messages.toString();
            log.error("Invalid amount value: {}", errorMessage);
            model.addAttribute("errorMessage",errorMessage);
        }
        catch (Exception e){
            model.addAttribute("errorMessage", e.getMessage());
        }

        populateModelAttributes(model);
        return "add-fx-deal";
    }

    private void populateModelAttributes(Model model) {
        List<CurrencyCode> allCurrencies = currencyService.getAllCurrencies();
        model.addAttribute("allCurrencies", allCurrencies);
        model.addAttribute("fxDealDto", new FxDealDto());
    }
}