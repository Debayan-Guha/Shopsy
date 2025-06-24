package com.ecom.shopsy.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class Product {

    int id;
    
    @Pattern(regexp = "^([A-Za-z\\s){1,2}[A-Za-z]+$")
    @NotNull(message = "Name Field Cannot be Empty")
    String name;

    @Pattern(regexp = "^[A-Za-z]+$")
    @NotNull(message = "Name Field Cannot be Empty")
    String category;

    @Pattern(regexp = "^[A-Za-z\\s]{1,2}[A-Za-z]+$")
    @NotNull(message = "Name Field Cannot be Empty")
    String color;

    @Min(value=10 , message="Must be greater than rs.10")
    double price;

    @NotNull
    int stock;

    @NotNull(message="Must be True or False")
    @Pattern(regexp = "^[a-zA-Z]+$")
    boolean isAvailable;

}
