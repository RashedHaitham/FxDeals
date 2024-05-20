package com.bloomberg.warehouse.repository;

import com.bloomberg.warehouse.model.entity.FxDeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FxDealRepositoryImpl implements FxDealDAO {

    @Autowired
    private FxDealRepository repository;

    @Override
    public void insertFxDeal(FxDeal fxDeal) {
        repository.save(fxDeal);
    }

    @Override
    public Optional<FxDeal> getFxDeal(int fxDealId) {
        return repository.findById(fxDealId);
    }
}
