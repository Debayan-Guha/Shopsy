package com.shopsy.ecom_api.Entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="login")
//use getter setter to prevent Looping problem like toString()->in @Transactional
@Getter
@Setter
public class LoginEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Log_Id")//we have to explicitly set name if we use this in referencedcolumnname
    private int logId;

    @Column(name="userId",unique = true)
    private String userId;
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "login_role",joinColumns=@JoinColumn(name="log_ID",referencedColumnName = "Log_Id"),inverseJoinColumns = @JoinColumn(name="role_ID",referencedColumnName = "id"))
    private Set<RoleEntity> role;

    @OneToOne(fetch = FetchType.LAZY)
    private CustomerEntity customerId;
    @OneToOne(fetch = FetchType.LAZY)
    private SellerEntity sellerId;
    @OneToOne(fetch = FetchType.LAZY)
    private AdminEntity adminId;

    
    
    
}
