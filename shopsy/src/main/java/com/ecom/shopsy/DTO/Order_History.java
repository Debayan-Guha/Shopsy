package com.ecom.shopsy.DTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Order_History {
    
    private int id;
    private LocalDateTime date;
    private List<OrderDetailsDisplay> details=new ArrayList<>();
}
