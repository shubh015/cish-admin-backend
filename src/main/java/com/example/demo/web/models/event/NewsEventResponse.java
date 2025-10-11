package com.example.demo.web.models.event;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class NewsEventResponse {
    private Long id;
    private String name;
    private Date date;
    private String title;
    private String type;
    private List<String> images;
}   