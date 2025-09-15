package com.example.demo.web.models.user;

import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class RegisterRequest {
  @NotBlank @Size(min=3, max=100) private String username;
  @NotBlank @Email private String email;
  @NotBlank @Size(min=8, max=128) private String password;
}

