package com.ecom.shopsy.DTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ecom.shopsy.Entity.Order_Details;

import lombok.Data;

@Data
public class Order_History {
    
    public int id;
    public LocalDateTime date;
    List<OrderDetailsDisplay> details=new ArrayList<>();
}
