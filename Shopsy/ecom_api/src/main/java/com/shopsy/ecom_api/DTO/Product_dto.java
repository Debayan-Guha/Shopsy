package com.shopsy.ecom_api.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Product_dto {
    
    public String id;
    
    @NotNull
    public String name;

    @NotNull
    public String category;

    @NotNull
    public int stock;
    
    @NotNull
    public double price;
}
