package com.shopsy.ecom_api.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopsy.ecom_api.DTO.Buy_dto;
import com.shopsy.ecom_api.DTO.Customer_dto;
import com.shopsy.ecom_api.DTO.OrderDetails_dto;
import com.shopsy.ecom_api.DTO.OrderHistory_dto;
import com.shopsy.ecom_api.DTO.Product_dto;
import com.shopsy.ecom_api.Entity.CustomerEntity;
import com.shopsy.ecom_api.Entity.LoginEntity;
import com.shopsy.ecom_api.Entity.OrderDetailsEntity;
import com.shopsy.ecom_api.Entity.OrderHistoryEntity;
import com.shopsy.ecom_api.Entity.ProductEntity;
import com.shopsy.ecom_api.Entity.RoleEntity;
import com.shopsy.ecom_api.Enum.Roles;
import com.shopsy.ecom_api.Exception.CustomerException;
import com.shopsy.ecom_api.Exception.ProductException;
import com.shopsy.ecom_api.Repository.CustomerRepo;
import com.shopsy.ecom_api.Repository.LoginRepo;
import com.shopsy.ecom_api.Repository.OrderHistoryRepo;
import com.shopsy.ecom_api.Repository.ProductRepo;
import com.shopsy.ecom_api.Repository.RoleRepo;
import com.shopsy.ecom_api.Service.Interface.interCustomerService;


import jakarta.transaction.Transactional;

@Service
public class CustomerService implements interCustomerService{

    @Autowired
    private CustomerRepo cr;
    @Autowired
    private ProductRepo pr;
    @Autowired
    private OrderHistoryRepo oh;
    @Autowired
    private RoleRepo rr;
    @Autowired
    private LoginRepo lr;
    
    private ModelMapper mapper=new ModelMapper();

    //creating instance of PasswordEncoder
    @Bean
    private BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder(12);
    }

    //Cenerating Customer ID
    private String generateCustomerId()
    {
        //Get the count of Existing Customer
        long count=cr.count();
        //Format the id with leading zeros(c_1001)
        return String.format("c_%04d", count+1);
    }

    //generate order History id
    private String generateOrderHistoryId()
    {
        //Get the count of Existing Order History
        long count=oh.count();
        //Format the id with leading zeros(c_1001)
        return String.format("oh_%04d", count+1);
    }

    //Customer Register
    @Transactional
    public String register(Customer_dto body) {
        
        //convert DTO to Entity
        CustomerEntity cus_entity=mapper.map(body,CustomerEntity.class);
        cus_entity.setId(generateCustomerId());
        cus_entity.setPassword(bCryptPasswordEncoder().encode(body.getPassword()));
        cus_entity.setDate(LocalDateTime.now());

        //Add to login Table
        LoginEntity login=new LoginEntity();
        login.setUserId(cus_entity.getId());
        login.setPassword(cus_entity.getPassword());
        
        Set<RoleEntity> role=new HashSet<>();
        //fetching role id from role db
        RoleEntity roleEntity=rr.getByRoleName(Roles.ROLE_CUSTOMER.toString());
        role.add(roleEntity);
        login.setRole(role);

        login.setCustomerId(cus_entity);

        //saving to mapped by for cascade save
        cus_entity.setLog(login);

        cr.save(cus_entity);

        return "Successfully Added";
    }

    public String update(String id,Customer_dto body) {

        //fetching customer details And Update 
        CustomerEntity cus_entity=cr.getById(id);
        mapper.map(body,cus_entity);
        cus_entity.setPassword(bCryptPasswordEncoder().encode(body.getPassword()));

        //fetch login table detail to reset password
        LoginEntity loginEntity=lr.getByUserId(id);
        loginEntity.setPassword(cus_entity.getPassword());

        cr.save(cus_entity);
        return "Successfully updated";
    }

    //whole method is atomic
   @Transactional
    public String buy(String customerId, List<Buy_dto> body) {
    
    //"find"->select all from a partiular field(MEANING)
    //"get"->fetch a particular tuple by a primary key(MEANING)

    // 1. Fetch customer (throw exception if not found)
    CustomerEntity customer = cr.getById(customerId);
    if (customer == null) {
        throw new CustomerException("Customer not found: " + customerId);
    }

    // 2. Create OrderHistory (not saved yet)
    OrderHistoryEntity orderHistory = new OrderHistoryEntity();
    orderHistory.setId(generateOrderHistoryId());
    orderHistory.setCustomer(customer);
    orderHistory.setDate(LocalDateTime.now());

    // 3. Process each item in the order
    List<OrderDetailsEntity> orderDetails = new ArrayList<OrderDetailsEntity>();
    List<ProductEntity> productsToUpdate = new ArrayList<ProductEntity>();
    double total = 0.0;

    for (Buy_dto item : body) {       
        
        if (!pr.existsById(item.getProduct_id())) {
            throw new ProductException("Product not found: " + item.getProduct_id());
        }
        //fetching product by product id given by user during buying
        ProductEntity product = pr.getById(item.getProduct_id());

        // Check stock
        if (product.getStock() < item.getQuantity()) {
            throw new ProductException("Not enough stock for: " + product.getName());
        }

        // Build OrderDetails
        OrderDetailsEntity detail = new OrderDetailsEntity();
        detail.setOrderHistoryEntity(orderHistory); // Link to parent
        detail.setProduct(product);
        detail.setProductName(product.getName());
        detail.setQuantity(item.getQuantity());
        detail.setPrice(product.getPrice());
        //adding to the list
        orderDetails.add(detail);

        // calculate total bill
        total += product.getPrice() * item.getQuantity();
        //Update stock after reduction
        product.setStock(product.getStock() - item.getQuantity());
        productsToUpdate.add(product);
    }

    //for getting benefits of cascade
    orderHistory.setDetails(orderDetails);
    //Save OrderHistory FIRST (to establish FK)
    oh.save(orderHistory);

    //Update product stocks
    pr.saveAll(productsToUpdate);

    return "Order placed successfully! Total: " + total;
}
    
    @Cacheable(value = "Order History",key="#userId")
    public List<OrderHistory_dto> history(String userId) {
        
        CustomerEntity customerEntity=cr.getById(userId);
        //Order history list of customer
        List<OrderHistory_dto> orderHistory_list=new ArrayList<>();
        for(OrderHistoryEntity orderHistoryEntity:customerEntity.getOrder())
        {
            OrderHistory_dto orderHistory_dto=new OrderHistory_dto();
            orderHistory_dto.setOrderHistoryid(orderHistoryEntity.getId());
            orderHistory_dto.setTime(orderHistoryEntity.getDate());

            List<OrderDetails_dto> orderDetailsDto_list=new ArrayList<>();
            for(OrderDetailsEntity orderDetailsEntity:orderHistoryEntity.getDetails())
            {
                OrderDetails_dto orderDetails_dto=new OrderDetails_dto();
                orderDetails_dto.setId(orderDetailsEntity.getId());
                orderDetails_dto.setProductName(orderDetailsEntity.getProductName());
                orderDetails_dto.setPrice(orderDetailsEntity.getPrice());
                orderDetails_dto.setQuantity(orderDetailsEntity.getQuantity()); 

                orderDetailsDto_list.add(orderDetails_dto);
            }
            orderHistory_dto.setOrderDetails(orderDetailsDto_list);

            orderHistory_list.add(orderHistory_dto);
        }
        return orderHistory_list;
    }
   
    @Cacheable(value = "Customer_Product_category",key = "#category+'-'+#sort+'-'+#page+'-'+#size")
    public List<Product_dto> category(String category, String sort, int page, int size) {
        
        if(sort.equalsIgnoreCase("low"))
        {
            sort="ASC";
        }
        else
        {
            sort="DESC";
        }
        Sort s=Sort.by(Sort.Direction.fromString(sort),"price");
        Pageable pageable=PageRequest.of(page, size,s);
        Page<ProductEntity> productEntities_page=pr.findByCategory(category,pageable);

        List<Product_dto> product_dto_list=new ArrayList<>();
        for(ProductEntity productEntity:productEntities_page)
        {            
            Product_dto product_dto=mapper.map(productEntity, Product_dto.class);
            
            product_dto_list.add(product_dto);
        }
        return product_dto_list;
    }

    @Cacheable(value = "Customer_Product_showAll",key="#page + '-' + #size")//import from this location->org.springframework.cache.annotation.Cacheable
    public List<Product_dto> showAll(int page, int size) {
        Pageable pageable=PageRequest.of(page, size);
        Page<ProductEntity> product_page=pr.findAll(pageable);

        List<Product_dto> product_dto_list=new ArrayList<>();
        for(ProductEntity productEntity:product_page)
        {
            Product_dto product_dto=mapper.map(productEntity,Product_dto.class);
            
            product_dto_list.add(product_dto);
        }
        return product_dto_list;
    }
    
}
