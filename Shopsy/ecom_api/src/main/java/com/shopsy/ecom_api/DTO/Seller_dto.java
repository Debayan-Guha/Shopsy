package com.shopsy.ecom_api.DTO;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Seller_dto extends BaseUserDTO{
    
    @NotNull
    public String shopName;
    
    
}
