package com.example.sale.campaign.managment.system.repository;

import com.example.sale.campaign.managment.system.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface ProductRepo extends JpaRepository<Product,String> {

}
