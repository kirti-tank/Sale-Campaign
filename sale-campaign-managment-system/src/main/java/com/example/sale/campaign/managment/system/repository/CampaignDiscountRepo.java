package com.example.sale.campaign.managment.system.repository;

import com.example.sale.campaign.managment.system.model.CampaignDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CampaignDiscountRepo extends JpaRepository<CampaignDiscount,Long> {
    List<CampaignDiscount> findBySaleCampaign_EndDate(LocalDate today);
    List<CampaignDiscount> findBySaleCampaign_startDate(LocalDate today);

    @Query("SELECT cd.discount FROM CampaignDiscount cd WHERE cd.product.productId = :productId AND :today BETWEEN cd.saleCampaign.startDate AND cd.saleCampaign.endDate")
    Optional<Double> findActiveDiscountByProductId(@Param("productId") String productId,
                                                   @Param("today") LocalDate today);
}
