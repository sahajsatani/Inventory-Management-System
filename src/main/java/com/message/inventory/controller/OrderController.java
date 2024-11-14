//package com.message.inventory.controller;
//
//import com.message.inventory.model.Entity.Order;
//import com.message.inventory.service.OrderService;
////import com.razorpay.RazorpayClient;
////import com.razorpay.RazorpayException;
////import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/order")
//public class OrderController {
//    @Autowired
//    OrderService orderService;
//
//    @PostMapping("/createOrder")
//    public ResponseEntity<?> makeProduct(@RequestBody Order order) {
//        return orderService.generateOrder(order);
//    }
//
//    @GetMapping("/getOrder")
//    public ResponseEntity<?> getOrder(@RequestParam int id) {
//        return orderService.getOrder(id);
//    }
//
//}
