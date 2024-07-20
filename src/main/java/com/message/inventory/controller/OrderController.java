package com.message.inventory.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.message.inventory.model.Invoice;
import com.message.inventory.model.Order;
import com.message.inventory.repositories.InvoiceRepo;
import com.message.inventory.service.InvoiceService;
import com.message.inventory.service.OrderService;
//import com.razorpay.RazorpayClient;
//import com.razorpay.RazorpayException;
//import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/makeorder")
    public ResponseEntity<?> makeProduct(@RequestBody Order order) {
        return orderService.makeProduct(order);
    }

    @GetMapping("/getOrder")
    public ResponseEntity<?> getOrder(@RequestParam int id) {
        return orderService.getOrder(id);
    }

}
