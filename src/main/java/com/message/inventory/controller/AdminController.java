package com.message.inventory.controller;

import com.message.inventory.model.Admin;
import com.message.inventory.model.DTO.Stock;
import com.message.inventory.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("/add")
    public ResponseEntity<?> newAdmin(@RequestBody Admin admin) {
        return adminService.newAdmin(admin);
    }


    @PostMapping("/addStoke")
    public ResponseEntity<?> addStock(@RequestBody List<Stock> list) {
        return adminService.addStock(list);
    }
}
