package com.example.demo.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.demo.repository.MediaFileRepository;
import com.example.demo.web.models.media.MediaFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MediaFileService {

    private final MediaFileRepository repository;

    public void saveMedia(String type, List<String> urls) {
        urls.forEach(url -> repository.save(MediaFile.builder().type(type).url(url).build()));
    }

    public List<MediaFile> getMedia(String type, String role ) {

              if(role != null ){
            if(role.equalsIgnoreCase("admin"))
               return repository.findByTypeIgnoreCaseAndIspublishedFalseAndIsactiveTrue(type);
            if(role.equalsIgnoreCase("creator"))
                return repository.findByTypeIgnoreCaseAndBacktocreatorTrue(type);     
        }
        
        return repository.findByTypeIgnoreCaseAndIspublishedTrueAndIsactiveTrue(type);
    }
}
