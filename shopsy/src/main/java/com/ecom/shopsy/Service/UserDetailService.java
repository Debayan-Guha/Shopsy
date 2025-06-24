package com.ecom.shopsy.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecom.shopsy.DTO.Customer;
import com.ecom.shopsy.Entity.CustomerEntity;
import com.ecom.shopsy.Repository.CustomerRepo;

@Service
public class UserDetailService implements UserDetailsService{

    @Autowired
    private CustomerRepo cr;

     private String customerName = "";
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomerEntity customer=cr.getByName(username);
        Customer cust=new Customer();
        cust.setName(customer.getName());
        cust.setPh(customer.getPh());
        cust.setEmail(customer.getEmail());
        cust.setAddress(customer.getAddress());
        cust.setPassword(customer.getPassword());
        
        customerName=username;
        
        return new Customer(cust);
    }
    public String customerget()
    {
        return customerName;
    }
    
}
