package com.example.demo.service;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.repository.UserRepository;
import com.example.demo.web.models.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException{
    User user = userRepository.findByUsername(usernameOrEmail)
                 .or(() -> userRepository.findByEmail(usernameOrEmail))
                 .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    return new org.springframework.security.core.userdetails.User(
         user.getUsername(),
         user.getPasswordHash(),
         user.getEnabled(),
         true,
         true,
         user.getAccountNonLocked(),
         user.getRoles().stream()
             .map(r -> new SimpleGrantedAuthority(r.getName()))
             .collect(Collectors.toList())
    );
  }
}
