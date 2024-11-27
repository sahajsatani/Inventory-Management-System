package com.message.inventory.model.DTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    @Column(nullable = false)
    private int productId;
    @Column(nullable = false)
    private int qty;
}
