package com.example.demo.web.models.event;



import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;@Data
@Entity
@Table(name = "news_event")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Date date;
    private String title;
    private String type;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    private String createdby;

    @Builder.Default
    @Column(name = "ispublished", nullable = false)
    private Boolean ispublished = false;

    @Builder.Default
    @Column(name = "isactive", nullable = false)
    private Boolean isactive = true;

    @Builder.Default
    @Column(name = "backtocreator", nullable = false)
    private Boolean backtocreator = false;

    @OneToMany(mappedBy = "newsEvent", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<NewsEventImage> images = new ArrayList<>();

    // existing helper
    public void addImage(String url, boolean thumbnail) {
        addImage(url, thumbnail, false);
    }

    // ✅ new helper with banner flag
    public void addImage(String url, boolean thumbnail, boolean banner) {
        NewsEventImage img = new NewsEventImage();
        img.setImageUrl(url);
        img.setThumbnail(thumbnail);
        img.setBanner(banner);
        img.setNewsEvent(this);
        images.add(img);
    }
}
