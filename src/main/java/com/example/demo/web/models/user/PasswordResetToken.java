package com.example.demo.web.models.user;



import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="password_reset_tokens")
@Data @NoArgsConstructor
public class PasswordResetToken {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional=false, fetch = FetchType.LAZY)
  @JoinColumn(name="user_id")
  private User user;

  @Column(nullable=false, unique=true)
  private UUID token;

  @Column(name="expires_at", nullable=false)
  private OffsetDateTime expiresAt;

  private boolean used = false;

  @Column(name="created_at", updatable=false)
  private OffsetDateTime createdAt = OffsetDateTime.now();
}
