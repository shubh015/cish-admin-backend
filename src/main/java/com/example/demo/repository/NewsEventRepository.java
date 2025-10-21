package com.example.demo.repository;



import com.example.demo.web.models.event.NewsEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsEventRepository extends JpaRepository<NewsEvent, Long> {
    List<NewsEvent> findByType(String type);

     List<NewsEvent> findByTypeIgnoreCaseAndIspublishedTrueAndIsactiveTrue(String type);
    List<NewsEvent> findByTypeIgnoreCaseAndIspublishedFalseAndIsactiveTrue(String type);
    List<NewsEvent> findByTypeIgnoreCaseAndBacktocreatorTrue(String type);

}
