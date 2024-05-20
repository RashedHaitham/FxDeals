package com.bloomberg.warehouse.repository;

import com.bloomberg.warehouse.model.entity.FxDeal;

import java.util.Optional;

public interface FxDealDAO {

    void insertFxDeal(FxDeal fxDeal);

    Optional<FxDeal> getFxDeal(int fxDealId);
}
