package com.message.inventory.service;

import com.message.inventory.model.Product;
import com.message.inventory.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    ProductRepo productRepo;
    public ResponseEntity<?> addProduct(Product product) {
        try{
            productRepo.save(product);
            return new ResponseEntity<>("Saved", HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
