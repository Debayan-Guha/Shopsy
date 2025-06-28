package com.ecom.shopsy.DTO;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seller {
    
    private String id;
    
    @NotNull
    private String name;

    @NotNull
    private long ph;

    @NotNull
    //@Pattern(regexp = "^[A-Za-z&\\-\\s]{3,50}$",message = "Shop name must be 3–50 characters, letters, spaces, &, or - only")
    private String  shopName;

    @NotNull
    //@Pattern(
  //regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",message = "Invalid email format")
    private String email;

    @NotNull
    private String address;

    @NotNull
    //@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$",message = "Password must be at least 8 characters long and include uppercase, lowercase, digit, and special character")
    private String password;

    private List<Product> products=new ArrayList<>();
    
}
