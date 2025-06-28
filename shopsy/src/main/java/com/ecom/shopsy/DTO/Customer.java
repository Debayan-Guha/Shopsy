package com.ecom.shopsy.DTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

  private String id;
  @NotNull
  private String name;

  @NotNull
  private long ph;

  @NotNull
  // @Pattern( regexp =
  // "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",message = "Invalid email
  // format")
   private String email;

  @NotNull
  private String address;

  @NotNull
  // @Pattern(regexp =
  // "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$",message = "Password
  // must be at least 8 characters long and include uppercase, lowercase, digit,
  // and special character")
  private String password;

}
