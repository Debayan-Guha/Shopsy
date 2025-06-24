package com.ecom.shopsy.Entity;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="Customers")
public class CustomerEntity {
    
     @Id
     @GeneratedValue(strategy=GenerationType.IDENTITY)
    protected int id;

    protected String name;
    
    protected long ph;
    
    @Column(unique = true)
    protected String email;
    
    protected String address;

    protected String password;

    @OneToMany(mappedBy = "customer")
    List<OrderEntity> orders=new ArrayList<>();

}
