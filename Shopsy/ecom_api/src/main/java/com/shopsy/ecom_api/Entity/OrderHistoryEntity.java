package com.shopsy.ecom_api.Entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "order_history")
@Data
public class OrderHistoryEntity {

    @Id
    private String id;

    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "cust_id", nullable = false)
    private CustomerEntity customer;

    @OneToMany(mappedBy = "orderHistoryEntity",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<OrderDetailsEntity> details;

}
