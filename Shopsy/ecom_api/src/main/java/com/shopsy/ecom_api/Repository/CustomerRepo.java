package com.shopsy.ecom_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopsy.ecom_api.Entity.CustomerEntity;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerEntity,String> {

    
} 
