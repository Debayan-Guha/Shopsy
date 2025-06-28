package com.ecom.shopsy.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecom.shopsy.DTO.Admin;
import com.ecom.shopsy.DTO.Customer;
import com.ecom.shopsy.DTO.Seller;
import com.ecom.shopsy.Entity.AdminEntity;
import com.ecom.shopsy.Entity.CustomerEntity;
import com.ecom.shopsy.Entity.LoginEntity;
import com.ecom.shopsy.Entity.RolesEntity;
import com.ecom.shopsy.Entity.SellerEntity;
import com.ecom.shopsy.Enum.Roles;
import com.ecom.shopsy.Repository.AdminRepo;
import com.ecom.shopsy.Repository.CustomerRepo;
import com.ecom.shopsy.Repository.LoginRepo;
import com.ecom.shopsy.Repository.RolesRepo;
import com.ecom.shopsy.Repository.SellerRepo;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class AdminService {

    @Autowired
    private AdminRepo ar;
    @Autowired
    private RolesRepo rr;
    @Autowired
    private CustomerRepo cr;
    @Autowired
    private SellerRepo sr;
    @Autowired
    private LoginRepo lr;

    private BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder(12);
    }

    public String adminRegister(Admin admin) {

        AdminEntity ae = new AdminEntity();
        ae.setId(admin_GenerateId());
        ae.setName(admin.getName());
        ae.setPassword(encoder().encode(admin.getPassword()));

        LoginEntity le = new LoginEntity();
        le.setName(ae.getName());
        le.setPassword(ae.getPassword());

        Set<RolesEntity> list = new HashSet<>();
        RolesEntity re = rr.getByuserroles(Roles.ROLE_ADMIN.toString());
        list.add(re);

        le.setRoles(list);
        le.setAdmin(ae);

        ae.setLogin(le);

        ar.save(ae);
        return "Saved Successfully";

    }

    private String admin_GenerateId() {
        Long count = ar.count();
        return "Ad_" + count++;
    }

    public String updateCustomerRole(Set<String> roles, String id) {

        //LoginEntity le = lr.getByCustomer(id);
        CustomerEntity ce=cr.getById(id);
        LoginEntity le=ce.getLogin();
        Set<RolesEntity> rolelist = new HashSet<>();
        for (String s : roles) {
            switch (s.toLowerCase()) {
                case "admin":
                    rolelist.add(rr.getByuserroles(Roles.ROLE_ADMIN.toString()));

                    break;
                case "seller":
                    rolelist.add(rr.getByuserroles(Roles.ROLE_SELLER.toString()));

                    break;

                case "customer":
                    rolelist.add(rr.getByuserroles(Roles.ROLE_CUSTOMER.toString()));

                    break;
            }
            le.setRoles(rolelist);
            lr.save(le);
        }
        return "Saved";
    }

    @Transactional
    public String updateSellerRole(Set<String> roles, String id) {
        //LoginEntity le = lr.getBySeller(id);
        SellerEntity se=sr.getById(id);
        LoginEntity le=se.getLogin();
        Set<RolesEntity> rolelist = new HashSet<>();
        for (String s : roles) {
            switch (s.toLowerCase()) {
                case "admin":
                    rolelist.add(rr.getByuserroles(Roles.ROLE_ADMIN.toString()));

                    break;
                case "seller":
                    rolelist.add(rr.getByuserroles(Roles.ROLE_SELLER.toString()));

                    break;

                case "customer":
                    rolelist.add(rr.getByuserroles(Roles.ROLE_CUSTOMER.toString()));

                    break;
            }
            le.setRoles(rolelist);
            lr.save(le);
        }
        return "Saved";
    }

    public List<Customer> allCustomer() {
        List<CustomerEntity> ce = cr.findAll();
        List<Customer> list = new ArrayList<>();
        for (CustomerEntity customer : ce) {
            Customer cus = new Customer();
            cus.setName(customer.getName());
            cus.setAddress(customer.getAddress());
            cus.setEmail(customer.getEmail());
            cus.setId(customer.getId());
            cus.setPh(customer.getPh());

            list.add(cus);
        }
        return list;
    }

    public List<Seller> allSeller() {
        List<SellerEntity> se = sr.findAll();
        List<Seller> sel = new ArrayList<>();
        for (SellerEntity sell : se) {
            Seller s = new Seller();
            s.setId(sell.getId());
            s.setName(sell.getName());
            s.setAddress(sell.getAddress());
            s.setEmail(sell.getEmail());
            s.setShopName(sell.getShopName());
            s.setPh(sell.getPh());

            sel.add(s);
        }
        return sel;
    }

    public List<Admin> allAdmin() {
        List<AdminEntity> ae = ar.findAll();
        List<Admin> list = new ArrayList<>();
        for (AdminEntity adm : ae) {
            Admin admin = new Admin();
            admin.setId(adm.getId());
            admin.setName(adm.getName());

            list.add(admin);
        }
        return list;
    }

    public String updateAdmin(Admin admin, String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateAdmin'");
    }

}
