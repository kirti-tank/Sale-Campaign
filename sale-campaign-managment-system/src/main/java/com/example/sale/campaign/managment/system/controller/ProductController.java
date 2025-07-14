package com.example.sale.campaign.managment.system.controller;

import com.example.sale.campaign.managment.system.DTO.CampaignInfoDTO;
import com.example.sale.campaign.managment.system.DTO.PageInfoDTO;
import com.example.sale.campaign.managment.system.DTO.SaleCampaignRequestDTO;
import com.example.sale.campaign.managment.system.service.CampaignInfoService;
import com.example.sale.campaign.managment.system.service.CampaignSchedulerService;
import com.example.sale.campaign.managment.system.service.CampaignService;
import com.example.sale.campaign.managment.system.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/campaign")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CampaignService campaignService;

    @Autowired
    CampaignSchedulerService campaignSchedulerService;

    @Autowired
    CampaignInfoService campaignInfoService;

    @PostMapping("products/add")
    public ResponseEntity<?> saveProductData() {
        String result = productService.addProduct();
        return ResponseEntity.ok(result);
    }

    @PostMapping("campaign")
        public ResponseEntity<String> startCampaigns(@RequestBody SaleCampaignRequestDTO saleCampaignRequestDTO) {
        campaignService.createCampaign(saleCampaignRequestDTO);
        return ResponseEntity.ok("Campaign created successfully");
    }

    @GetMapping("product/{page}/{pagesize}")
    public ResponseEntity<PageInfoDTO> getProductData(@PathVariable int page, @PathVariable int pagesize){
        Pageable pageable = PageRequest.of(page,pagesize);
        PageInfoDTO response = productService.getProducts(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("campaignInfo")
    public ResponseEntity<?> getCampaignInfo(){
        CampaignInfoDTO response = campaignService.getAllCampaignsByStatus();
        return ResponseEntity.ok(response);
    }
}
