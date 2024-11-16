package com.message.inventory.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "tblInvoice")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int invoiceId;

    private LocalDate date = LocalDate.now();

    private String transactionId;

    private int productId;

    private int gst;

    private int qty;

    private int totalAmount;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customerPayment;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order orderID;

}
