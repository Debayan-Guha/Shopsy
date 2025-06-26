package com.ecom.shopsy.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecom.shopsy.Entity.OrderEntity;

@Repository
public interface OrderRepo extends JpaRepository<OrderEntity,Integer>{

    List<OrderEntity> findByCustomer_Id(String id);
    
}
