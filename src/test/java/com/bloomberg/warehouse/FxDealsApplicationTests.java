package com.bloomberg.warehouse;

import com.bloomberg.warehouse.controller.FxDealController;
import com.bloomberg.warehouse.exceptions.CurrencyNotFoundException;
import com.bloomberg.warehouse.exceptions.DuplicateDealException;
import com.bloomberg.warehouse.exceptions.SameCurrencyException;
import com.bloomberg.warehouse.model.dto.FxDealDto;
import com.bloomberg.warehouse.model.entity.FxDeal;
import com.bloomberg.warehouse.service.CurrencyService;
import com.bloomberg.warehouse.service.FxDealService;
import com.neovisionaries.i18n.CurrencyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FxDealControllerTest {

    @Mock
    private FxDealService fxDealService;

    @Mock
    private CurrencyService currencyService;

    @InjectMocks
    private FxDealController fxDealController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShowAddFxDealForm() {
        Model model = mock(Model.class);
        List<CurrencyCode> currencies = Collections.singletonList(CurrencyCode.USD);

        when(currencyService.getAllCurrencies()).thenReturn(currencies);

        String viewName = fxDealController.showAddFxDealForm(model);

        assertEquals("add-fx-deal", viewName);
        verify(model).addAttribute("allCurrencies", currencies);
        verify(model).addAttribute("fxDealDto", new FxDealDto());
    }

    @Test
    void testCreateFXDeal_Success() {
        FxDealDto dealDTO = new FxDealDto();
        FxDeal fxDeal = new FxDeal();
        Model model = mock(Model.class);

        when(fxDealService.addFxDeal(dealDTO)).thenReturn(fxDeal);

        String viewName = fxDealController.createFXDeal(dealDTO, model);

        assertEquals("add-fx-deal", viewName);
        verify(model).addAttribute("successMessage", "FX deal created successfully");
    }

    @Test
    void testCreateFXDeal_SameCurrencyException() {
        FxDealDto dealDTO = new FxDealDto();
        Model model = mock(Model.class);

        when(fxDealService.addFxDeal(dealDTO)).thenThrow(new SameCurrencyException("Same currency not allowed"));

        String viewName = fxDealController.createFXDeal(dealDTO, model);

        assertEquals("add-fx-deal", viewName);
        verify(model).addAttribute("errorMessage", "Same currency not allowed");
    }

    @Test
    void testCreateFXDeal_DuplicateDealException() {
        FxDealDto dealDTO = new FxDealDto();
        Model model = mock(Model.class);

        when(fxDealService.addFxDeal(dealDTO)).thenThrow(new DuplicateDealException("Deal already exists"));

        String viewName = fxDealController.createFXDeal(dealDTO, model);

        assertEquals("add-fx-deal", viewName);
        verify(model).addAttribute("errorMessage", "Deal already exists");
    }

    @Test
    void testCreateFXDeal_CurrencyNotFoundException() {
        FxDealDto dealDTO = new FxDealDto();
        Model model = mock(Model.class);

        when(fxDealService.addFxDeal(dealDTO)).thenThrow(new CurrencyNotFoundException("Currency not found"));

        String viewName = fxDealController.createFXDeal(dealDTO, model);

        assertEquals("add-fx-deal", viewName);
        verify(model).addAttribute("errorMessage", "Currency not found: Currency not found");
    }
}
