package com.example.demo.web.models.event;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "news_event_image")
public class NewsEventImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "news_event_id")
    @JsonBackReference
    private NewsEvent newsEvent;
}
