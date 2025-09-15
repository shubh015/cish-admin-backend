package com.example.demo.service;


import com.example.demo.repository.LicensingTermRepository;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.TechnologyPersonRepository;
import com.example.demo.repository.TechnologyRepository;
import com.example.demo.repository.rowmapper.TechnologyMapper;
import com.example.demo.web.models.tchnology.LicensingTerm;
import com.example.demo.web.models.tchnology.LicensingTermDto;
import com.example.demo.web.models.tchnology.Person;
import com.example.demo.web.models.tchnology.PersonDto;
import com.example.demo.web.models.tchnology.Technology;
import com.example.demo.web.models.tchnology.TechnologyCreateDto;
import com.example.demo.web.models.tchnology.TechnologyPerson;
import com.example.demo.web.models.tchnology.TechnologyPersonId;
import com.example.demo.web.models.tchnology.TechnologyUpdateDto;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TechnologyService {

    private final TechnologyRepository technologyRepository;
    private final PersonRepository personRepository;
    private final LicensingTermRepository licensingTermRepository;
    private final TechnologyPersonRepository technologyPersonRepository;

    /**
     * Create a technology with optional inventors and licensing terms.
     */
    @Transactional
    public Technology create(TechnologyCreateDto dto, String currentUser) {
        Technology t = Technology.builder()
                .name(dto.getName())
                .category(dto.getCategory())
                .shortDescription(dto.getShortDescription())
                .techDetails(dto.getTechDetails())
                .icNo(dto.getIcNo())
                .yearDevelopment(dto.getYearDevelopment())
                .yearRelease(dto.getYearRelease())
                .yearCommercialization(dto.getYearCommercialization())
                .targetCustomers(dto.getTargetCustomers())
                .createdBy(currentUser)
                .build();

        // first persist technology to get an id
        t = technologyRepository.save(t);

        // save persons (inventors) and create join rows
        List<PersonDto> inventors = dto.getInventors();
        if (inventors != null) {
            for (PersonDto p : inventors) {
                // find existing or create without lambda capture issue
                Person person = personRepository.findByFullName(p.getFullName()).orElse(null);
                if (person == null) {
                    person = personRepository.save(
                            Person.builder()
                                    .fullName(p.getFullName())
                                    .email(p.getEmail())
                                    .affiliation(p.getAffiliation())
                                    .build()
                    );
                }

                // create TechnologyPerson link
                TechnologyPersonId tpId = new TechnologyPersonId(t.getId(), person.getId());
                TechnologyPerson tp = TechnologyPerson.builder()
                        .id(tpId)
                        .technology(t)
                        .person(person)
                        .role("INVENTOR")
                        .build();
                technologyPersonRepository.save(tp);
            }
        }

        // save licensing terms
        List<LicensingTermDto> terms = dto.getLicensingTerms();
        if (terms != null) {
            for (LicensingTermDto l : terms) {
                LicensingTerm term = LicensingTerm.builder()
                        .technology(t)
                        .natureOfLicense(l.getNatureOfLicense())
                        .durationYears(l.getDurationYears())
                        .territory(l.getTerritory())
                        .licenseFeeCents(l.getLicenseFeeCents())
                        .rebatePercent(l.getRebatePercent())
                        .royalty(l.getRoyalty())
                        .notes(l.getNotes())
                        .build();
                licensingTermRepository.save(term);
            }
        }

        return t;
    }

    /**
     * Search or list technologies with paging.
     */
    public Page<Technology> search(String q, Pageable pageable) {
        if (q == null || q.isBlank()) {
            return technologyRepository.findAll(pageable);
        }
        return technologyRepository.searchByText(q, pageable);
    }

    /**
     * Soft delete (mark inactive)
     */
    @Transactional
    public void softDelete(Long id, String username) {
        Technology t = technologyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Technology not found: " + id));
        t.setIsActive(false);
        t.setUpdatedBy(username);
        technologyRepository.save(t);
    }

    /**
     * Update an existing technology.
     */
    @Transactional
    public Technology update(Long id, TechnologyUpdateDto dto, String username) {
        Technology t = technologyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Technology not found: " + id));

        // apply changes via MapStruct mapper
        TechnologyMapper.INSTANCE.updateEntityFromDto(dto, t);
        t.setUpdatedBy(username);

        return technologyRepository.save(t);
    }
}
