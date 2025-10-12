package com.example.demo.web.models.content;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "key_content")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KeyContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content_key", nullable = false)
    private String contentKey;

    private String title;
    private String description;
    private String imageUrl;

    private LocalDate date;
    private LocalDate postDate;
    private LocalDate lastDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
