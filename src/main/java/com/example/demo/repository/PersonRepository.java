package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.web.models.tchnology.Person;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    /**
     * Find a person by full name (case sensitive).
     */
    Optional<Person> findByFullName(String fullName);

    /**
     * Optional: case-insensitive search.
     */
    Optional<Person> findByFullNameIgnoreCase(String fullName);
}
