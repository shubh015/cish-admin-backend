package com.example.demo.service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.web.models.role.Role;
import com.example.demo.web.models.user.AuthRequest;
import com.example.demo.web.models.user.AuthResponse;
import com.example.demo.web.models.user.RegisterRequest;

import com.example.demo.web.models.user.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepo;
  private final RoleRepository roleRepo;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authManager;
  private final JwtService jwtService;

  @Transactional
  public void register(RegisterRequest req) {
    if (userRepo.existsByUsername(req.getUsername())) throw new IllegalArgumentException("username taken");
    if (userRepo.existsByEmail(req.getEmail())) throw new IllegalArgumentException("email taken");

    User u = new User();
    u.setUsername(req.getUsername());
    u.setEmail(req.getEmail());
    u.setMobile(req.getMobile());
    u.setPasswordHash(passwordEncoder.encode(req.getPassword()));
    Role defaultRole = roleRepo.findByName("ROLE_USER")
        .orElseGet(() -> roleRepo.save(new Role(null, "ROLE_USER", "Default user")));
    u.getRoles().add(defaultRole);
    userRepo.save(u);
  }
public AuthResponse login(AuthRequest req) {
    Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
    );

    UserDetails ud = (UserDetails) auth.getPrincipal();
    String token = jwtService.generateToken(ud);

    // Extract roles from UserDetails
    List<String> roles = ud.getAuthorities().stream()
            .map(authObj -> authObj.getAuthority())
            .toList();

    return new AuthResponse(token, "Bearer", roles);
}

}

