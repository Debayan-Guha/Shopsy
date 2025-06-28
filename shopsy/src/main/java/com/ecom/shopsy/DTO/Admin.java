package com.ecom.shopsy.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Admin {
    
    private String id;

    @NotNull
    private String name;
    @NotNull
    private String password;
    
}
