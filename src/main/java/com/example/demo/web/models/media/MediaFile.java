package com.example.demo.web.models.media;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "media_files")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediaFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // image or video
    private String url;

         // ✅ Newly added flags
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
