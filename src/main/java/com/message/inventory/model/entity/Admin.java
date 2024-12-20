package com.message.inventory.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbladmin")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int adminId;

    @Pattern(regexp = "^[A-Za-z ]+$")
    @Column(nullable = false)
    private String name;

    @Email
    @Column(nullable = false)
    private String email;

    @Pattern(regexp = "^[1-9][0-9]{9}$")
    @Column(nullable = false)
    private String phone;

    @Pattern(regexp = "^[1-9][0-9]{9}$")
    @Column(nullable = false)
    private String whatsappNumber;

    @Column(nullable = false)
    private String password;
}
