package com.message.inventory.model.entity;

import com.message.inventory.model.invoiceDtos.Status;
import com.message.inventory.model.embedable.Address;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "tblorder")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderId;

    private LocalDate orderDate = LocalDate.now();

    private LocalDate shippingDate = orderDate.plusDays(5);

    @Min(1) @Max(10)
    private int qty;

    private int price;

    private int totalAmount;

    private Status status = Status.ISSUED;

    private Address address;

    //Mapping
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(mappedBy = "orderID",cascade = CascadeType.ALL)
    private Invoice invoiceId;

    public Order(int orderId) {
        this.orderId = orderId;
    }
}
