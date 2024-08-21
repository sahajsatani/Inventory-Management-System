package com.message.inventory.model.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbladmin")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int adminId;

    @Pattern(regexp = "^[A-Za-z ]+$")
    private String name;

    @Email
    private String email;

    @Pattern(regexp = "^[1-9][0-9]{9}$")
    private String phone;

    @Pattern(regexp = "^[1-9][0-9]{9}$")
    private String whatsappNumber;

    private String password;

}
