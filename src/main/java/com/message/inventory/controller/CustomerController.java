package com.message.inventory.controller;

import com.message.inventory.model.Entity.Admin;
import com.message.inventory.model.Entity.Customer;
import com.message.inventory.model.Entity.Order;
import com.message.inventory.service.CustomerService;
import com.message.inventory.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @Autowired
    OrderService orderService;
    @PostMapping("/createOrder")
    public ResponseEntity<?> makeProduct(@RequestBody Order order) {
        return orderService.generateOrder(order);
    }
    @GetMapping("/getOrder")
    public ResponseEntity<?> getOrder(@RequestParam int id) {
        return orderService.getOrder(id);
    }
    @PostMapping("/login")
    public String login(@RequestBody Customer customer){
        return customerService.verify(customer);
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Customer customer) {
        return customerService.register(customer);
    }
}
