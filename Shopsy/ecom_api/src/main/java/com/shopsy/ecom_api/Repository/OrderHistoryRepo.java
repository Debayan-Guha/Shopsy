package com.shopsy.ecom_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopsy.ecom_api.Entity.OrderHistoryEntity;

@Repository
public interface OrderHistoryRepo extends JpaRepository<OrderHistoryEntity,String>{
    
}
