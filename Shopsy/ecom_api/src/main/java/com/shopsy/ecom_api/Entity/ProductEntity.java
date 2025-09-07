package com.shopsy.ecom_api.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class ProductEntity {
    
    @Id
    private String id;

    private String name;

    private String category;

    private double price;

    private int stock;

    //JSON ignore in Spring Boot often comes into play when you're directly sending entity objects from your database to the controller and then to the JSON response.while using pagination
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Seller_id",nullable = false)
    private SellerEntity seller;
}
