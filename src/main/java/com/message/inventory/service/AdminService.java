package com.message.inventory.service;

import com.message.inventory.model.entity.Admin;
import com.message.inventory.model.DTO.Stock;
import com.message.inventory.repositories.AdminRepo;
import com.message.inventory.repositories.ProductRepo;
import com.message.inventory.configuration.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    AdminRepo adminRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
    @Autowired
    ProductRepo productRepo;
    @Autowired
    JwtService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;
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
    public ResponseEntity<?> verify(Admin admin) {
        Authentication authentication;
        try{
             authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(admin.getEmail(), admin.getPassword()));
        }
        catch (Exception ex){
            if(adminRepo.findByEmail(admin.getEmail()) == null)
                return new ResponseEntity<>(admin.getEmail()+" Not found",HttpStatus.NOT_FOUND);
            else
                return new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (authentication.isAuthenticated())
            return new ResponseEntity<>(jwtService.generateToken(admin.getEmail()),HttpStatus.FOUND);
        else
            return new ResponseEntity<>(admin.getEmail()+" don't matched with password",HttpStatus.NOT_FOUND);
    }
}
