package com.message.inventory.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tblproduct")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int productId;

    @Pattern(regexp = "^[A-Za-z0-9 ]+$", message = "Please enter alphabet only")
    @Column(nullable = false)
    private String productName;

    @Min(1) @Max(100)
    @Column(nullable = false)
    private int gst;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int inventoryStoke;

    private int sold;

    //Mapping
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "product")
    private List<Order> orders = new ArrayList<>();
    public Product(int productId) {
        this.productId = productId;
    }
}
