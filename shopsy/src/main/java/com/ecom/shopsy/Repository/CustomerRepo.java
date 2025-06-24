package com.ecom.shopsy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.shopsy.Entity.CustomerEntity;

public interface CustomerRepo extends JpaRepository<CustomerEntity,Integer>{

    public CustomerEntity getByName(String username);

    
} 
