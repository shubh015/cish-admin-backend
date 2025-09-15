package com.example.demo.web.models.user;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class AuthResponse {
     private String accessToken; 
     private String tokenType = "Bearer"; 
      private List<String> roles;
    }

