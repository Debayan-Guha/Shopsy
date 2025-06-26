package com.ecom.shopsy.DTO;

import lombok.Data;

@Data
public class OrderDetailsDisplay {
    
    private int id;

    private String product_name;
    private String color;
    private int quantity;
    private double price;    
}
