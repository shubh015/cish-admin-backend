package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.web.models.tchnology.Technology;

public interface TechnologyRepository extends JpaRepository<Technology, Long> {

    // Simple derived method
    boolean existsByIcNo(String icNo);

    // Postgres full-text native query with countQuery for pagination
    @Query(
        value = """
            SELECT t.* FROM technology t
            WHERE to_tsvector('english', coalesce(t.name,'') || ' ' || coalesce(t.short_description,'') || ' ' || coalesce(t.tech_details::text,'')) 
                  @@ plainto_tsquery(:q)
            ORDER BY ts_rank(
              to_tsvector('english', coalesce(t.name,'') || ' ' || coalesce(t.short_description,'')),
              plainto_tsquery(:q)
            ) DESC
            """,
        countQuery = """
            SELECT count(1) FROM technology t
            WHERE to_tsvector('english', coalesce(t.name,'') || ' ' || coalesce(t.short_description,'') || ' ' || coalesce(t.tech_details::text,'')) 
                  @@ plainto_tsquery(:q)
            """,
        nativeQuery = true
    )
    Page<Technology> searchByText(@Param("q") String q, Pageable pageable);
}