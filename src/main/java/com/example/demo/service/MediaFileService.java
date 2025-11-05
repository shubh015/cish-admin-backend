package com.example.demo.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.repository.MediaFileRepository;
import com.example.demo.web.models.media.MediaFile;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MediaFileService {

    private final MediaFileRepository repository;

    public void saveMedia(String type, List<String> urls) {
        urls.forEach(url -> repository.save(MediaFile.builder().type(type).url(url).build()));
    }

      public void saveAll(List<MediaFile> files) {
        repository.saveAll(files);
    }

    public List<MediaFile> getMedia(String type, String role ) {

              if(role != null ){
            if(role.equalsIgnoreCase("admin"))
               return repository.findByTypeIgnoreCaseAndIspublishedFalseAndIsactiveTrue(type);
            if(role.equalsIgnoreCase("creator"))
                return repository.findByTypeIgnoreCaseAndBacktocreatorTrueAndIsactiveTrue(type);     
        }
        
        return repository.findByTypeIgnoreCaseAndIspublishedTrueAndIsactiveTrue(type);
    }

    @Transactional
public void updateStatus(List<Long> ids, Boolean isPublished, Boolean isActive, Boolean backToCreator) {
    for (Long id : ids) {
        MediaFile emp = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Innovation not found with ID: " + id));

        if (isPublished != null) emp.setIspublished(isPublished);;
        if (isActive != null) emp.setIsactive(isActive);
        if (backToCreator != null) emp.setBacktocreator(backToCreator);

        repository.save(emp);
    }
}

}
