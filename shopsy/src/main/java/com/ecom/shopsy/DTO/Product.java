package com.ecom.shopsy.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class Product {

    private String id;

    // @Pattern(regexp = "^([A-Za-z\\s){1,2}[A-Za-z]+$")
    @NotNull(message = "Name Field Cannot be Empty")
    private String name;

    // @Pattern(regexp = "^[A-Za-z]+$")
    @NotNull(message = "Name Field Cannot be Empty")
    private String category;

    // @Pattern(regexp = "^[A-Za-z\\s]{1,2}[A-Za-z]+$")
    @NotNull(message = "Name Field Cannot be Empty")
    private String color;

    @Min(value = 10, message = "Must be greater than rs.10")
    private double price;

    @NotNull
    private int stock;

    // @Pattern(regexp = "^[a-zA-Z]+$")
    @NotNull(message = "Must be True or False")
    private String available;

}
