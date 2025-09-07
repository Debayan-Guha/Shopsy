package com.shopsy.ecom_api.Entity;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer")
public class CustomerEntity{

    @Id
    private String id;

    private String name;

    @Column(unique=true)
    private String email;

    @Column(name = "phone_number")
    private long phno;

    private String address;

    private String password;

    @Column(name = "registration_date")
    private LocalDateTime date;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "customer")
    private List<OrderHistoryEntity> order;

    @OneToOne(mappedBy = "customerId",cascade = CascadeType.ALL,fetch=FetchType.LAZY)
    private LoginEntity log;
}
