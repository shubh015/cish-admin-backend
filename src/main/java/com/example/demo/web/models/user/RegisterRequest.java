package com.example.demo.web.models.user;

import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
public class RegisterRequest {
  @NotBlank @Size(min=3, max=100) private String username;
  @NotBlank @Email private String email;
    @NotBlank
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Mobile number must be 10 to 15 digits")
    private String mobile;
    private String desig;

  @NotBlank @Size(min=8, max=128) private String password;
}

