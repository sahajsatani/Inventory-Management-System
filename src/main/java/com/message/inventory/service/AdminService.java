package com.message.inventory.service;

import com.message.inventory.model.Admin;
import com.message.inventory.model.DTO.Stock;
import com.message.inventory.model.Product;
import com.message.inventory.repositories.AdminRepo;
import com.message.inventory.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    AdminRepo adminRepo;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    @Autowired
    ProductRepo productRepo;

    public ResponseEntity<?> newAdmin(Admin admin) {
        try{
            admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
            if (adminRepo.save(admin) != null) {
                return new ResponseEntity<>("Added new admin.", HttpStatus.CREATED);
            }
            return new ResponseEntity<>("Not added admin.", HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CREATED);
        }
    }

    public ResponseEntity<?> addStock(List<Stock> list) {
        try{
            boolean flag = true;
            for (Stock i : list) {
                if(productRepo.updateStock(i.getQty(),i.getProductId())==0)
                {
                    flag = false;
                }
            }
            if (flag){
                return new ResponseEntity<>("Stock added.",HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("Not all stock added.",HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> updateAdmin(Admin admin) {
        try{
            admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
            if (adminRepo.save(admin) != null) {
                return new ResponseEntity<>("Update details 🟢.", HttpStatus.CREATED);
            }
            return new ResponseEntity<>("Not update details 🔴", HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CREATED);
        }
    }
}
