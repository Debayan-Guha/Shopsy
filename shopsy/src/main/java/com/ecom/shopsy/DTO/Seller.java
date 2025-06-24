package com.ecom.shopsy.DTO;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Seller {
    
    @NotNull
    String name;

    @NotNull
    @Size(max = 10)
    long ph;

    @NotNull
    @Pattern(regexp = "^[A-Za-z&\\-\\s]{3,50}$",message = "Shop name must be 3–50 characters, letters, spaces, &, or - only")

    String  shopName;

    @NotNull
    @Pattern(
  regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",message = "Invalid email format")
    String email;

    @NotNull
    String address;

    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$",message = "Password must be at least 8 characters long and include uppercase, lowercase, digit, and special character")
    String password;

    List<Product> products=new ArrayList<>();
}
