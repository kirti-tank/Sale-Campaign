package com.example.sale.campaign.managment.system.repository;

import com.example.sale.campaign.managment.system.model.PricingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PricingHistoryRepo extends JpaRepository<PricingHistory,Integer> {

}
