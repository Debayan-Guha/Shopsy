package com.shopsy.ecom_api.Service.Interface;

import java.util.List;

import com.shopsy.ecom_api.DTO.Buy_dto;
import com.shopsy.ecom_api.DTO.Customer_dto;
import com.shopsy.ecom_api.DTO.OrderHistory_dto;
import com.shopsy.ecom_api.DTO.Product_dto;

public interface interCustomerService {

    public String register(Customer_dto body) ;

    public String update(String id,Customer_dto body);

    public String buy(String customerId,List<Buy_dto> body) ;

    public List<OrderHistory_dto> history(String userId) ;

    public List<Product_dto> category(String category, String sort, int page, int size) ;

    public List<Product_dto> showAll(int page, int size) ;
}
