package com.ecom.shopsy.Repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecom.shopsy.Entity.SellerEntity;

@Repository
public interface SellerRepo extends JpaRepository<SellerEntity,String>{

    SellerEntity getByName(String user);

    
} 
