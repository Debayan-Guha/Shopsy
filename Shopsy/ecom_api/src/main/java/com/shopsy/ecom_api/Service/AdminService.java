package com.shopsy.ecom_api.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopsy.ecom_api.Controller.Admin;
import com.shopsy.ecom_api.Controller.Customer;
import com.shopsy.ecom_api.Controller.Seller;
import com.shopsy.ecom_api.DTO.Admin_dto;
import com.shopsy.ecom_api.DTO.Customer_dto;
import com.shopsy.ecom_api.DTO.Seller_dto;
import com.shopsy.ecom_api.Entity.AdminEntity;
import com.shopsy.ecom_api.Entity.CustomerEntity;
import com.shopsy.ecom_api.Entity.LoginEntity;
import com.shopsy.ecom_api.Entity.RoleEntity;
import com.shopsy.ecom_api.Entity.SellerEntity;
import com.shopsy.ecom_api.Enum.Roles;
import com.shopsy.ecom_api.Repository.AdminRepo;
import com.shopsy.ecom_api.Repository.CustomerRepo;
import com.shopsy.ecom_api.Repository.LoginRepo;
import com.shopsy.ecom_api.Repository.RoleRepo;
import com.shopsy.ecom_api.Repository.SellerRepo;

import jakarta.transaction.Transactional;

@Service
public class AdminService {

    
    @Autowired
    private AdminRepo ar;
    @Autowired
    private LoginRepo lr;
    @Autowired
    private RoleRepo rr;
    @Autowired
    private CustomerRepo cr;
    @Autowired
    private SellerRepo sr;

    private ModelMapper mapper=new ModelMapper();
    
    private BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder(12);
    }

    //Cenerating Admin ID
    private String generateAdminId()
    {
        //Get the count of Existing admin
        long count=ar.count();
        //Format the id with leading zeros(c_1001)
        return String.format("a_%04d", count+1);
    }

    public String adminRegister(Admin_dto admin) {

        AdminEntity ae = new AdminEntity();
        ae.setId(generateAdminId());
        ae.setName(admin.getName());
        ae.setPassword(encoder().encode(admin.getPassword()));

        LoginEntity le = new LoginEntity();
        le.setUserId(ae.getId());
        le.setPassword(ae.getPassword());

        Set<RoleEntity> roleEntities = new HashSet<>();
        RoleEntity re = rr.getByRoleName(Roles.ROLE_ADMIN.toString());
        roleEntities.add(re);

        le.setRole(roleEntities);
        le.setAdminId(ae);

        ae.setLogin(le);

        ar.save(ae);
        return "Saved Successfully";

    }

    @Transactional
    public String updateUserRole(Set<String> roles, String id) {

        LoginEntity le = lr.getByUserId(id);
        Set<RoleEntity> rolelist = new HashSet<>();
        for (String s : roles) {
            switch (s.toLowerCase()) {
                case "admin":
                    rolelist.add(rr.getByRoleName(Roles.ROLE_ADMIN.toString()));

                    break;
                case "seller":
                    rolelist.add(rr.getByRoleName(Roles.ROLE_SELLER.toString()));

                    break;

                case "customer":
                    rolelist.add(rr.getByRoleName(Roles.ROLE_CUSTOMER.toString()));

                    break;
            }
            le.setRole(rolelist);
            lr.save(le);
        }
        return "Saved";
    }

    public List<Customer_dto> allCustomer() {
        List<CustomerEntity> ce = cr.findAll();
        //creating new list
        List<Customer_dto> list = new ArrayList<>();
        for (CustomerEntity customer : ce) {
            
            Customer_dto customer_dto=mapper.map(customer, Customer_dto.class);
            list.add(customer_dto);
        }
        return list;
    }

    public List<Seller_dto> allSeller() {
        List<SellerEntity> se = sr.findAll();
        //creating new list
        List<Seller_dto> list = new ArrayList<>();
        for (SellerEntity seller : se) {
            
            Seller_dto seller_dto=mapper.map(seller, Seller_dto.class);
            list.add(seller_dto);
        }
        return list;
    }

    public List<Admin_dto> allAdmin() {
        List<AdminEntity> ae = ar.findAll();
        //creating new list
        List<Admin_dto> list = new ArrayList<>();
        for (AdminEntity admin : ae) {
            
            Admin_dto admin_dto=mapper.map(admin, Admin_dto.class);
            list.add(admin_dto);
        }
        return list;
    }

    public String updateAdmin(Admin admin, String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateAdmin'");
    }
}
