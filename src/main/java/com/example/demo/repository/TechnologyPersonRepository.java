package com.example.demo.repository;




import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.web.models.tchnology.TechnologyPerson;
import com.example.demo.web.models.tchnology.TechnologyPersonId;

public interface TechnologyPersonRepository extends JpaRepository<TechnologyPerson, TechnologyPersonId> {
}
