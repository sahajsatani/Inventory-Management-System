package com.message.inventory.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tblcustomer")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int customerId;

    @Pattern(regexp = "^[A-Z a-z]+$", message = "Please enter alphabet only")
    private String name;

    @Pattern(regexp = "^[0-9]{10}$", message = "Please enter 10 number only")
    private String phone;

    @Email
    @Column(unique = true)
    private String email;

    private String password;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "customerPayment")
    private List<Invoice> invoices = new ArrayList<>();


    public Customer(int customerId) {
        this.customerId = customerId;
    }
}
