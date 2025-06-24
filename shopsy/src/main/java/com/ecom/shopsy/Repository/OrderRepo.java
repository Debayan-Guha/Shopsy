package com.ecom.shopsy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.shopsy.Entity.OrderEntity;

public interface OrderRepo extends JpaRepository<OrderEntity,Integer>{
    
}
