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

    // ✅ Rename this column to match JSON key “url”
    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    // ✅ Add this new field to handle the "thumbnail" flag
    @Column(name = "thumbnail", nullable = false)
    private Boolean thumbnail = false;

    @ManyToOne
    @JoinColumn(name = "news_event_id")
    @JsonBackReference
    private NewsEvent newsEvent;
}
