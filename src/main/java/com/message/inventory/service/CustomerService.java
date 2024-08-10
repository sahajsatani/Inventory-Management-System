package com.message.inventory.service;

import com.message.inventory.model.Embedable.Address;
import com.message.inventory.model.Customer;
import com.message.inventory.repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepo customerRepo;

    public ResponseEntity<?> addCustomer(Customer customer) {
        try {

            if(customerRepo.save(customer)!=null)
                return new ResponseEntity<>("Added Customer.", HttpStatus.CREATED);
            else
                return new ResponseEntity<>("Not added customer.", HttpStatus.CREATED);

        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
