package com.example.demo.web.models.event;



import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "news_event")
public class NewsEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Date date;
    private String title;
    private String type; // newsEvent or vksa

    @OneToMany(mappedBy = "newsEvent", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<NewsEventImage> images = new ArrayList<>();

    // helper to add image
    public void addImage(String url) {
        NewsEventImage img = new NewsEventImage();
        img.setImageUrl(url);
        img.setNewsEvent(this);
        images.add(img);
    }
}
