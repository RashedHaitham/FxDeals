package com.bloomberg.warehouse.repository;

import com.bloomberg.warehouse.model.entity.FxDeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FxDealRepository extends JpaRepository<FxDeal, Long> {

}
