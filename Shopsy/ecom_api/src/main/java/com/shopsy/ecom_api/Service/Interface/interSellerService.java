package com.shopsy.ecom_api.Service.Interface;

import java.util.List;

import org.springframework.data.domain.Page;

import com.shopsy.ecom_api.DTO.Product_dto;
import com.shopsy.ecom_api.DTO.Seller_dto;

public interface interSellerService {
    
    public String register(Seller_dto body) ;

    public String update(String id,Seller_dto body);
    
    public String addProduct(List<Product_dto> body,String id) ;

    public String updateProduct(String product_id, Product_dto body,String seller_id);

    public List<Product_dto> category(String category,String sort, int page, int size) ;

    public List<Product_dto> searchByName(String name, String sort, int page, int size) ;

    public List<Product_dto> showAll(int page, int size) ;
}
