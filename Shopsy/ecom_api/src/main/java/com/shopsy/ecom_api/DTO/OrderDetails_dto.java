package com.shopsy.ecom_api.DTO;

import lombok.Data;

@Data
public class OrderDetails_dto {
    
    private int id;

    private  String productName;

    private int quantity;

    private double price;

}