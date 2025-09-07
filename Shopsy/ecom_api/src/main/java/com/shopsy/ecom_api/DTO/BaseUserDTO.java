package com.shopsy.ecom_api.DTO;

import java.util.Set;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public abstract class BaseUserDTO {
    
    @NotNull
    protected String name;
    @NotNull
    protected String email;
    @NotNull
    protected String address;
    @NotNull
    @Digits(integer=10,fraction = 0)
    protected Long phno;
    @NotNull
    protected String password;
}
