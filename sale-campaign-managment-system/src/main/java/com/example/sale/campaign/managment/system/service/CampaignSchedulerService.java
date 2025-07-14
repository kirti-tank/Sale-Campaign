package com.example.sale.campaign.managment.system.service;

import com.example.sale.campaign.managment.system.model.CampaignDiscount;
import com.example.sale.campaign.managment.system.model.PricingHistory;
import com.example.sale.campaign.managment.system.model.Product;
import com.example.sale.campaign.managment.system.repository.CampaignDiscountRepo;
import com.example.sale.campaign.managment.system.repository.PricingHistoryRepo;
import com.example.sale.campaign.managment.system.repository.ProductRepo;
import com.example.sale.campaign.managment.system.repository.SaleCampaignRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class CampaignSchedulerService {

    @Autowired
    CampaignDiscountRepo campaignDiscountRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    SaleCampaignRepo saleCampaignRepo;

    @Autowired
    PricingHistoryRepo pricingHistoryRepo;

    //@Scheduled(cron = "0 0 0/3 * * *")
    @Scheduled(cron = "0 */3 * * * *")
    public void applyDiscount(){
        System.out.println("System checking every 1 minute"+ LocalTime.now());
        LocalDate today = LocalDate.now();
        List<CampaignDiscount> discounts = campaignDiscountRepo.findBySaleCampaign_startDate(today);

        for (CampaignDiscount cd : discounts){
            Product product = cd.getProduct();
            double discountedPrice = product.getMrp() * (1 - cd.getDiscount() / 100);
            if(product.getCurrentPrice() != discountedPrice){//System will check every minute for any campaign is there,here why this condition need
                double oldPrice = product.getCurrentPrice();
                product.setCurrentPrice(discountedPrice);
                productRepo.save(product);
                PricingHistory pricingHistory = new PricingHistory(product ,
                        oldPrice,
                        discountedPrice,
                        "Campaign started: " + cd.getSaleCampaign().getCampaignName(),LocalDate.now());
                pricingHistoryRepo.save(pricingHistory);
            }
        }
    }
    //@Scheduled(cron = "0 0 0/3 * * *")
    @Scheduled(cron = "0 * * * * *")
    public void revertDiscounts() {
        System.out.println("Revert-System checking every 1 minute"+ LocalTime.now());
        LocalDate today = LocalDate.now();
        List<CampaignDiscount> endedDiscounts = campaignDiscountRepo.findBySaleCampaign_EndDate(today);

        for (CampaignDiscount cd : endedDiscounts) {
            Product product = cd.getProduct();
            double mrp = product.getMrp();

            if (product.getCurrentPrice() != mrp) {
                double oldPrice = product.getCurrentPrice();
                product.setCurrentPrice(mrp);
                productRepo.save(product);

                PricingHistory history = new PricingHistory(product, oldPrice, mrp,
                        "Campaign Ended: " + cd.getSaleCampaign().getCampaignName(), today);
                pricingHistoryRepo.save(history);
            }
        }
    }
}
