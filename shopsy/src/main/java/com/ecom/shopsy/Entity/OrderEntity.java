package com.ecom.shopsy.Entity;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="Order_history")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    LocalDateTime date;
    
   @ManyToOne(fetch = FetchType.LAZY)
   CustomerEntity customer;

   @OneToMany(mappedBy = "order_history",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
   List<Order_Details> details;


    
    
}
