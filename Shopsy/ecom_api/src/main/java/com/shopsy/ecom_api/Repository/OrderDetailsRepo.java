package com.shopsy.ecom_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopsy.ecom_api.Entity.OrderDetailsEntity;

@Repository
public interface OrderDetailsRepo extends JpaRepository<OrderDetailsEntity,Integer>{
    
}
