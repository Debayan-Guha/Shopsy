package com.shopsy.ecom_api.DTO;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class OrderHistory_dto {
    
    private String orderHistoryid;
    private LocalDateTime time;
    private double totalBill;
    private List<OrderDetails_dto> orderDetails;

}
