package com.example.demo.web.models.media;


import java.util.Date;

import jakarta.persistence.*;
import lombok.*;@Entity
@Table(name = "media_files")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediaFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // image, video, abic, banner
    private String url;
    private Boolean thumbnail = false;

    private String title;

    private String description;

       private String createdby;

    @Column(name = "publish_date")
    private Date publishDate;

    // ✅ new banner link field
    @Column(name = "banner_link")
    private String bannerLink;

      @Column(name = "isbanner_first")
    private Boolean isBannerFirst;

    @Builder.Default
    @Column(name = "ispublished", nullable = false)
    private Boolean ispublished = false;

    @Builder.Default
    @Column(name = "isactive", nullable = false)
    private Boolean isactive = true;

    @Builder.Default
    @Column(name = "backtocreator", nullable = false)
    private Boolean backtocreator = false;
}
