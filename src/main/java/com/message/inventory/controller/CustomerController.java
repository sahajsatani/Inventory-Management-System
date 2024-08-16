package com.message.inventory.controller;

import com.message.inventory.model.Embedable.Address;
import com.message.inventory.model.Customer;
import com.message.inventory.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping("/addcustomer")
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer) {
        return customerService.addCustomer(customer);
    }

}
