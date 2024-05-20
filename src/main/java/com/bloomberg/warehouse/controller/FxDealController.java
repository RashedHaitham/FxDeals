package com.bloomberg.warehouse.controller;

import com.bloomberg.warehouse.exceptions.CurrencyNotFoundException;
import com.bloomberg.warehouse.exceptions.DuplicateDealException;
import com.bloomberg.warehouse.exceptions.SameCurrencyException;
import com.bloomberg.warehouse.model.dto.FxDealDto;
import com.bloomberg.warehouse.model.enums.Currency;
import com.bloomberg.warehouse.service.CurrencyService;
import com.bloomberg.warehouse.service.FxDealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        List<Currency> allCurrencies = currencyService.getAllCurrencies();
        model.addAttribute("allCurrencies", allCurrencies);
        model.addAttribute("currencySymbols", currencyService.getCurrencySymbols());
        model.addAttribute("fxDealDto", new FxDealDto());
        return "add-fx-deal";
    }

    @PostMapping("/deal")
    public String createFXDeal(FxDealDto dealDTO, Model model) {
        log.info("Received request to create FX deal: {}", dealDTO);

        try {
            FxDealDto createdDeal = fxDealService.addFxDeal(dealDTO);
            log.info("FX deal created successfully: {}", createdDeal);
            model.addAttribute("successMessage", "FX deal created successfully");
        } catch (SameCurrencyException | DuplicateDealException e) {
            log.error("Failed to create FX deal: {}", e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
        } catch (CurrencyNotFoundException e) {
            log.error("Currency not found: {}", e.getMessage());
            model.addAttribute("errorMessage", "Currency not found: " + e.getMessage());
        } catch (Exception e) {
            log.error("An error occurred while processing the request", e);
            model.addAttribute("errorMessage", "An error occurred while processing the request. Please try again later.");
        }
        List<Currency> allCurrencies = currencyService.getAllCurrencies();
        model.addAttribute("allCurrencies", allCurrencies);
        model.addAttribute("fxDealDto", new FxDealDto());
        model.addAttribute("currencySymbols", currencyService.getCurrencySymbols());

        return "add-fx-deal";
    }
}