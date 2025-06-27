package com.ecom.shopsy.DTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer implements UserDetails {

  @NotNull
  String name;

  @NotNull
  long ph;

  @NotNull
  // @Pattern( regexp =
  // "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",message = "Invalid email
  // format")
  String email;

  @NotNull
  String address;

  @NotNull
  // @Pattern(regexp =
  // "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$",message = "Password
  // must be at least 8 characters long and include uppercase, lowercase, digit,
  // and special character")
  String password;

  public Customer(Customer cust) {
    name = cust.getUsername();
    password = cust.getPassword();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List c = new ArrayList<>();
    c.add(new SimpleGrantedAuthority("ROLE_USER"));
    return c;
  }

  @Override
  public String getUsername() {
    return this.name;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

}
