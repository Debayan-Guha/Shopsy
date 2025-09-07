package com.shopsy.ecom_api.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopsy.ecom_api.Entity.ProductEntity;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity, String> {
    
    //we have to put entity class name in the place of table name.jpa will automatically take table name from class
    @Query("Select p from ProductEntity p where p.category=?1")
    public Page<ProductEntity> findByCategory(String category,Pageable pageable);

    @Query("select p from ProductEntity p where p.name=:name")
    public Page<ProductEntity> findByFilterAndSort(@Param("name") String filter,Pageable page);


}

