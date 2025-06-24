package com.ecom.shopsy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.shopsy.Entity.ProductEntity;

public interface ProductRepo extends JpaRepository<ProductEntity,Integer>{

    
} 
