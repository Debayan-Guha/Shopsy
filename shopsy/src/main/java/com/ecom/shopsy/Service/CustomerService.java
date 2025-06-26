package com.ecom.shopsy.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecom.shopsy.DTO.Customer;
import com.ecom.shopsy.DTO.Order;
import com.ecom.shopsy.DTO.OrderDetailsDisplay;
import com.ecom.shopsy.DTO.Order_History;
import com.ecom.shopsy.DTO.Product;
import com.ecom.shopsy.Entity.CustomerEntity;
import com.ecom.shopsy.Entity.LoginEntity;
import com.ecom.shopsy.Entity.OrderEntity;
import com.ecom.shopsy.Entity.Order_Details;
import com.ecom.shopsy.Entity.ProductEntity;
import com.ecom.shopsy.Entity.RolesEntity;
import com.ecom.shopsy.Enum.Roles;
import com.ecom.shopsy.ExceptionHandler.CustomerOrderException;
import com.ecom.shopsy.Repository.CustomerRepo;
import com.ecom.shopsy.Repository.OrderRepo;
import com.ecom.shopsy.Repository.ProductRepo;
import com.ecom.shopsy.Repository.RolesRepo;

import jakarta.transaction.Transactional;

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
    @Autowired
    private RolesRepo rr;
    
    private BCryptPasswordEncoder encoder()
    {
        return new BCryptPasswordEncoder(12);
    }
    @Transactional
    public String registerCustomer(Customer customer) {
        
        CustomerEntity ce=new CustomerEntity();
        ce.setId(customer_generateId());
        ce.setName(customer.getName());
        ce.setPh(customer.getPh());
        ce.setEmail(customer.getEmail());
        ce.setAddress(customer.getAddress());
        ce.setPassword(encoder().encode(customer.getPassword()));

        LoginEntity le=new LoginEntity();
        le.setName(ce.getName());
        le.setPassword(ce.getPassword());

        RolesEntity re=rr.getByuserroles(Roles.CUSTOMER.toString());
        System.out.println("Roles Check"+re.getUserroles()+" "+re.getId());
        Set<RolesEntity> list=new HashSet<>();
        list.add(re);
        le.setRoles(list);
        le.setCustomer(ce);

        ce.setLogin(le);

        cr.save(ce);
        return "Successfully created";
    }

    @Transactional
    public String buyList(List<Order> order) {

        //created to store the each order contains order characteristics like(Color,price,etc)
        List<Order_Details> od=new ArrayList<>(); 
        //to store order in the db  .  OrderEntity is the order history
        OrderEntity oe=new OrderEntity();
        //iterate orders . each Order is wrapped under Order class
        for(Order or:order){
            String order_id=or.getId();
            //finding each product by the product id . product id given by user during buying
            ProductEntity product=pr.getById(order_id);
            if(product.isAvailable()==true)
            {
                if(product.getStock()>=or.getQuantity())
                {
                    //decrement stock of product from the product db
                    int AfterStock=product.getStock()-or.getQuantity();
                    if(AfterStock==0)
                    {
                        product.setAvailable(false);
                    }
                    //saving the modified product details
                    product.setStock(AfterStock);
                    pr.save(product);

                }
                else{
                    //if the product is out of stock
                    throw new CustomerOrderException(product.getName()+" Stock not available");
                }
                
            }
            else{
                //if the product is out of stock
                throw new CustomerOrderException(product.getName()+" not available");
            }
            //orderentity takes orderdetails list ->contains only inportantinfo about the product buy
            Order_Details details=new Order_Details();
            details.setProduct_name(product.getName());
            details.setColor(product.getColor());
            details.setQuantity(or.getQuantity());
            details.setPrice(product.getPrice());
            details.setOrder_history(oe);

            od.add(details);
        }
        //fetching user details who is requesting to create order
        CustomerEntity cust=cr.getByName(uds.getUser());
        oe.setDate(LocalDateTime.now());
        oe.setCustomer(cust);
        oe.setDetails(od);
        
        or.save(oe);
        return "Buy Successfully";

    }
    @Transactional
    public List<Order_History> orderHistory() {
        CustomerEntity ce=cr.getByName(uds.getUser());
        List<OrderEntity> oe=or.findByCustomer_Id(ce.getId());
        if(oe.isEmpty())
        {
            throw new CustomerOrderException("No order found");
        }
        List<Order_History> his=new ArrayList<>();

        for(OrderEntity i:oe){
            Order_History oh=new Order_History();
            oh.setId(i.getId());
            oh.setDate(i.getDate());            
            List<OrderDetailsDisplay> list=new ArrayList<>();

            List<Order_Details> det=i.getDetails();
            for (Order_Details j : det) {
                OrderDetailsDisplay odd=new OrderDetailsDisplay();
                odd.setId(j.getId());
                odd.setProduct_name(j.getProduct_name());
                odd.setColor(j.getColor());
                odd.setQuantity(j.getQuantity());
                odd.setPrice(j.getPrice());
                list.add(odd);
            }
            oh.setDetails(list);
            his.add(oh);
        }
        if(oe.isEmpty())
        {
            throw new CustomerOrderException("No orders");
        }        
        
        return his;
    }
    public List<Product> getAll() {
        System.out.println("Working");
        List<ProductEntity> p= pr.findAll();
        List<Product> pro=new ArrayList<>();
        for (ProductEntity i : p) {
             Product product=new Product();
             product.setId(i.getId());
             product.setName(i.getName());
             product.setCategory(i.getCategory());
             product.setColor(i.getColor());
             product.setPrice(i.getPrice());
             product.setStock(i.getStock());
             product.setAvailable(i.isAvailable());
             
             pro.add(product);
        }
        return pro;
    }

    private String customer_generateId() {
        // Get the count of existing sellers
        long count = cr.count();
        
        // Format the ID with leading zeros (sell_1001, sell_1002, etc.)
        return String.format("c_%04d", count + 1);
        
    }
}
