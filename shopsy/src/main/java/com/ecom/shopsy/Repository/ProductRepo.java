package com.ecom.shopsy.Repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecom.shopsy.Entity.ProductEntity;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity,String>{

    Set<ProductEntity> getByseller_id(String id);

    
} 
