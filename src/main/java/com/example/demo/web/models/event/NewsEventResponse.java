package com.example.demo.web.models.event;

import lombok.Data;

import java.util.Date;
import java.util.List;

import java.util.List;

@Data
public class NewsEventResponse {
    private Long id;
    private String name;
    private Date date;
    private String title;
    private String type;
    private Date startDate;
    private Date endDate;

    // ✅ Each image will have both "url" and "thumbnail"
    private List<ImageResponse> images;

    @Data
    public static class ImageResponse {
        private String url;
        private Boolean thumbnail;
    }
}
  