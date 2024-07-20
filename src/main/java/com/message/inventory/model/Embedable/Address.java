package com.message.inventory.model.Embedable;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Pattern(regexp = "^[0-9]+$", message = "Please enter only number")
    private String appartmentNo;

    @Pattern(regexp = "^[A-Za-z ]+$", message = "please enter only text")
    private String society;

    @Pattern(regexp = "^[A-Za-z ]+$", message = "please enter only text")
    private String area;

    @Pattern(regexp = "^[A-Za-z ]+$", message = "please enter only text")
    private String city;

    @Pattern(regexp = "^[A-Za-z ]+$", message = "please enter only text")
    private String state;

    @Pattern(regexp = "^[0-9]{6}+$", message = "Please enter only number")
    private String pincode;
}
