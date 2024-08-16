package com.message.inventory.controller;

import com.message.inventory.model.Admin;
import com.message.inventory.model.DTO.Stock;
import com.message.inventory.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/getCsrf")
    public org.springframework.security.web.server.csrf.CsrfToken getCsrf(HttpServletRequest request){
        return (org.springframework.security.web.server.csrf.CsrfToken) request.getAttribute("_csrf");
    }

    @GetMapping("/getSession")
    public String getSesssion(HttpServletRequest request){
        return "Hey "+request.getSession().getId();
    }
    @PostMapping("/add")
    public ResponseEntity<?> newAdmin(@RequestBody Admin admin) {
        return adminService.newAdmin(admin);
    }


    @PostMapping("/addStoke")
    public ResponseEntity<?> addStock(@RequestBody List<Stock> list) {
        return adminService.addStock(list);
    }
}
