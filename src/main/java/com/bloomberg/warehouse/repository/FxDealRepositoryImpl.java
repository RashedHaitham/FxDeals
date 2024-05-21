package com.bloomberg.warehouse.repository;

import com.bloomberg.warehouse.model.entity.FxDeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FxDealRepositoryImpl implements FxDealDAO {

    private final FxDealRepository repository;

   @Autowired
   @Lazy
   FxDealRepositoryImpl(FxDealRepository repository){
        this.repository = repository;
   }

    @Override
    public void insertFxDeal(FxDeal fxDeal) {
        repository.save(fxDeal);
    }

    @Override
    public Optional<FxDeal> getFxDeal(long fxDealId) {
        return repository.findById(fxDealId);
    }
}
