package com.example.demo.web.models.user;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResetPasswordRequest {
     @NotNull private UUID token; 
     @NotBlank @Size(min=8) private String newPassword;
     }
