package com.ecom.shopsy.Entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="Customer")
public class CustomerEntity {
    
     @Id
     @Column(unique=true)
    protected String id;
    
    protected String name;
    @Column(name="phone")
    protected long ph;
    
    @Column(unique = true)
    protected String email;
    
    protected String address;

    protected String password;

    @OneToOne(mappedBy = "customer",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private LoginEntity login;

    @OneToMany(mappedBy = "customer")
    List<OrderEntity> orders=new ArrayList<>();

}
