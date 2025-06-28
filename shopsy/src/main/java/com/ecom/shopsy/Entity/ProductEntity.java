package com.ecom.shopsy.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Product")
public class ProductEntity {

    @Id
    @Column(unique = true)
    protected String id;

    String name;
    String category;
    String color;
    double price;
    int stock;
    String available;

    @ManyToOne(fetch = FetchType.LAZY)
    SellerEntity seller;

}
