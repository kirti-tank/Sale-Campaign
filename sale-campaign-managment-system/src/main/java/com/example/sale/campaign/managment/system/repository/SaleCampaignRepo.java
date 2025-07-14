package com.example.sale.campaign.managment.system.repository;

import com.example.sale.campaign.managment.system.model.SaleCampaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SaleCampaignRepo extends JpaRepository<SaleCampaign,Long> {

    @Query("select s from SaleCampaign s where s.endDate < :today")
    List<SaleCampaign> findPastCampaigns(@Param("today") LocalDate today);

    @Query("select s from SaleCampaign s where s.startDate <= :today and s.endDate >= :today ")
    List<SaleCampaign> findCurrentCampaigns(@Param("today") LocalDate today);

    @Query("select s from SaleCampaign s where s.startDate > :today")
    List<SaleCampaign> findFutureCampaigns(@Param("today") LocalDate today);
}
