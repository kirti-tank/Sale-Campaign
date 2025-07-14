package com.example.sale.campaign.managment.system.service;

import com.example.sale.campaign.managment.system.DTO.CampaignDiscountDTO;
import com.example.sale.campaign.managment.system.DTO.CampaignInfoDTO;
import com.example.sale.campaign.managment.system.DTO.SaleCampaignRequestDTO;
import com.example.sale.campaign.managment.system.model.CampaignDiscount;
import com.example.sale.campaign.managment.system.model.Product;
import com.example.sale.campaign.managment.system.model.SaleCampaign;
import com.example.sale.campaign.managment.system.repository.CampaignDiscountRepo;
import com.example.sale.campaign.managment.system.repository.PricingHistoryRepo;
import com.example.sale.campaign.managment.system.repository.ProductRepo;
import com.example.sale.campaign.managment.system.repository.SaleCampaignRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CampaignService {

    @Autowired
    SaleCampaignRepo saleCampaignRepo;
    @Autowired
    PricingHistoryRepo priceHistoryRepo;
    @Autowired
    CampaignDiscountRepo campaignDiscountRepo;
    @Autowired
    ProductRepo productRepo;

    public void createCampaign(SaleCampaignRequestDTO saleCampaignRequestDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate startDate = LocalDate.parse(saleCampaignRequestDTO.getStartDate(), formatter);
        LocalDate endDate = LocalDate.parse(saleCampaignRequestDTO.getEndDate(), formatter);

        SaleCampaign saleCampaign = new SaleCampaign(saleCampaignRequestDTO.getCampaignName(), startDate,endDate);
        saleCampaignRepo.save(saleCampaign);

        for (CampaignDiscountDTO campaignDiscountDTO : saleCampaignRequestDTO.getListOfCampaign()){
            Product product = productRepo.findById(campaignDiscountDTO.getProductId()).orElse(null);
            if (product == null){
                throw new RuntimeException("Product Not Found");
            }
            CampaignDiscount campaignDiscount = new CampaignDiscount(product,saleCampaign,campaignDiscountDTO.getDiscount());
            campaignDiscountRepo.save(campaignDiscount);
        }
    }

    public CampaignInfoDTO getAllCampaignsByStatus() {
        LocalDate today = LocalDate.now();
        List<SaleCampaign> pastCampaign = saleCampaignRepo.findPastCampaigns(today);
        List<SaleCampaign> currentCampaign = saleCampaignRepo.findCurrentCampaigns(today);
        List<SaleCampaign> futureCampaign = saleCampaignRepo.findFutureCampaigns(today);
        return new CampaignInfoDTO(pastCampaign,currentCampaign,futureCampaign);
    }
}
