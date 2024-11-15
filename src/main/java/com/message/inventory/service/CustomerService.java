package com.message.inventory.service;

import com.message.inventory.Jwts.JwtService;
import com.message.inventory.model.Entity.Customer;
import com.message.inventory.repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtService jwtService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public ResponseEntity<?> register(Customer customer) {
        try {
            customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
            if(customerRepo.save(customer)!=null)
                return new ResponseEntity<>("Added Customer.", HttpStatus.CREATED);
            else
                return new ResponseEntity<>("Not added customer.", HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> verify(Customer customer) {
        Authentication authentication;
        try{
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(customer.getEmail(), customer.getPassword()));
        }
        catch (Exception ex){
            return new ResponseEntity<>(customer.getEmail()+" Not found",HttpStatus.NOT_FOUND);
        }
        if (authentication.isAuthenticated()) {
            return new ResponseEntity<>(jwtService.generateToken(customer.getEmail()),HttpStatus.FOUND);
        }
        return new ResponseEntity<>(customer.getEmail()+" Not found",HttpStatus.NOT_FOUND);
    }
}
