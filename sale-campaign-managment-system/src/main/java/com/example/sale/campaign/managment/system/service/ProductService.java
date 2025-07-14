package com.example.sale.campaign.managment.system.service;

import com.example.sale.campaign.managment.system.DTO.PageInfoDTO;
import com.example.sale.campaign.managment.system.DTO.ProductResponseDTO;
import com.example.sale.campaign.managment.system.model.CampaignDiscount;
import com.example.sale.campaign.managment.system.model.Product;
import com.example.sale.campaign.managment.system.model.SaleCampaign;
import com.example.sale.campaign.managment.system.repository.CampaignDiscountRepo;
import com.example.sale.campaign.managment.system.repository.ProductRepo;
import com.example.sale.campaign.managment.system.repository.SaleCampaignRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;
    @Autowired
    SaleCampaignRepo saleCampaignRepo;
    @Autowired
    CampaignDiscountRepo campaignDiscountRepo;

    public String addProduct() {
        try{
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = getClass().getResourceAsStream("/products_data.json");
            List<Product> housingList = mapper.readValue(inputStream, new TypeReference<>(){});
            productRepo.saveAll(housingList);
            return "Data loaded successfully";
        }catch (Exception e){
            return "Error" +e.getMessage();
        }
    }

    public PageInfoDTO getProducts(Pageable pageable) {
        Page<Product> productPage = productRepo.findAll(pageable);
        LocalDate today = LocalDate.now();
        List<ProductResponseDTO> productDTOList = new ArrayList<>();

        for (Product product : productPage.getContent()){
            Double discount = campaignDiscountRepo.findActiveDiscountByProductId(product.getProductId(),today).orElse(null);
            System.out.println(discount);
            ProductResponseDTO dto = new ProductResponseDTO(
                    product.getProductId(),
                    product.getTitle(),
                    product.getMrp(),
                    product.getCurrentPrice(),
                    product.getInventoryCount(),
                    discount
            );
            productDTOList.add(dto);
        }
        return new PageInfoDTO(productDTOList,productPage.getNumber(),productPage.getSize(),productPage.getTotalPages());
    }
}




