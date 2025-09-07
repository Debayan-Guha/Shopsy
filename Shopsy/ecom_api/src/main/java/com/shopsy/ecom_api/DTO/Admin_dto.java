package com.shopsy.ecom_api.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Admin_dto {
    
    @NotNull
    private String name;
    @NotNull
    private String password;
}
