package com.ecom.shopsy.Entity;

import java.util.HashSet;
import java.util.Set;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="Sellers")
public class SellerEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    int id;

    String name;
    
    long ph;
    String shopName;
    String email;    
    String address;
    String password;

    @OneToMany(mappedBy="seller",cascade = CascadeType.ALL)
    Set<ProductEntity> products=new HashSet<>();
    
}
