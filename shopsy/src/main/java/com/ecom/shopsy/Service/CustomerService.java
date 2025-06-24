package com.ecom.shopsy.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.OrderAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecom.shopsy.DTO.Customer;
import com.ecom.shopsy.DTO.Order;
import com.ecom.shopsy.DTO.Product;
import com.ecom.shopsy.Entity.CustomerEntity;
import com.ecom.shopsy.Entity.OrderEntity;
import com.ecom.shopsy.Entity.Order_Details;
import com.ecom.shopsy.Entity.ProductEntity;
import com.ecom.shopsy.ExceptionHandler.CustomerOrderException;
import com.ecom.shopsy.Repository.CustomerRepo;
import com.ecom.shopsy.Repository.OrderRepo;
import com.ecom.shopsy.Repository.ProductRepo;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class CustomerService {

    @Autowired
    public CustomerRepo cr;
    @Autowired
    private ProductRepo pr;
    @Autowired
    private UserDetailService uds;
    @Autowired
    private OrderRepo or;
    
    private BCryptPasswordEncoder encoder()
    {
        return new BCryptPasswordEncoder(12);
    }
    public String registerCustomer(Customer customer) {
        
        CustomerEntity ce=new CustomerEntity();
        ce.setName(customer.getName());
        ce.setPh(customer.getPh());
        ce.setEmail(customer.getEmail());
        ce.setAddress(customer.getAddress());
        ce.setPassword(encoder().encode(customer.getPassword()));
        cr.save(ce);
        return "Successfully created";
    }

    @Transactional
    public String buyList(List<Order> order) {

        List<Order_Details> od=new ArrayList<>(); 

        OrderEntity oe=new OrderEntity();
        
        for(Order or:order){
            int order_id=or.getId();
            ProductEntity product=pr.getById(order_id);
            if(product.isAvailable()==true)
            {
                if(product.getStock()>=or.getQuantity())
                {
                    int AfterStock=product.getStock()-or.getQuantity();
                    if(AfterStock==0)
                    {
                        product.setAvailable(false);
                    }
                    product.setStock(AfterStock);
                    pr.save(product);

                }
                else{
                    throw new CustomerOrderException(product.getName()+" Stock not available");
                }
                
            }
            else{
                throw new CustomerOrderException(product.getName()+" not available");
            }
            Order_Details details=new Order_Details();
            details.setProduct_name(product.getName());
            details.setColor(product.getColor());
            details.setQuantity(or.getQuantity());
            details.setPrice(product.getPrice());
            details.setOrder_history(oe);

            od.add(details);
        }
        CustomerEntity cust=cr.getByName(uds.customerget());
        oe.setDate(LocalDateTime.now());
        oe.setCustomer(cust);
        oe.setDetails(od);
        
        or.save(oe);
        return "Buy Successfully";

    }

    public List<Order> orderHistory() {
        return null;
    }
    
}
