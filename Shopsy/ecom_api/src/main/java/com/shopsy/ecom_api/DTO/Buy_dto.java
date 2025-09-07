package com.shopsy.ecom_api.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Buy_dto {

    @NotNull(message = "product  id missing")
    public String product_id;
    @NotNull(message = "quantity missing")
    public int quantity;
    
}
