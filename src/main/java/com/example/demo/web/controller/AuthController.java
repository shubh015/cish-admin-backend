package com.example.demo.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.AuthService;
import com.example.demo.service.PasswordResetService;
import com.example.demo.web.models.user.AuthRequest;
import com.example.demo.web.models.user.AuthResponse;
import com.example.demo.web.models.user.ForgotPasswordRequest;
import com.example.demo.web.models.user.RegisterRequest;
import com.example.demo.web.models.user.ResetPasswordRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;
  private final PasswordResetService resetService;

  @PostMapping("/register")
  public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
    authService.register(req);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest req) {
    AuthResponse r = authService.login(req);
    return ResponseEntity.ok(r);
  }

  @PostMapping("/forgot-password")
  public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest req) {
    resetService.requestReset(req.getEmail());
    // Always return 200 to avoid email enumeration
    return ResponseEntity.ok().build();
  }

  @PostMapping("/reset-password")
  public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest req) {
    resetService.resetPassword(req.getToken(), req.getNewPassword());
    return ResponseEntity.ok().build();
  }
}
