package com.example.demo.web.controller;

import com.example.demo.repository.TechnologyRepository;
import com.example.demo.repository.rowmapper.TechnologyMapper;
import com.example.demo.service.TechnologyService;
import com.example.demo.web.models.tchnology.Technology;
import com.example.demo.web.models.tchnology.TechnologyCreateDto;
import com.example.demo.web.models.tchnology.TechnologyDto;
import com.example.demo.web.models.tchnology.TechnologyUpdateDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/technologies")
@RequiredArgsConstructor
public class TechnologyController {

    private final TechnologyService technologyService;
    private final TechnologyRepository techRepo;

    /**
     * Create a new technology
     */
    @PostMapping
    public ResponseEntity<TechnologyDto> create(@Valid @RequestBody TechnologyCreateDto dto) {
        Technology saved = technologyService.create(dto, "system"); // user hardcoded for now
        return ResponseEntity.status(HttpStatus.CREATED).body(TechnologyMapper.INSTANCE.toDto(saved));
    }

    /**
     * List/search technologies
     */
    @GetMapping
    public Page<TechnologyDto> list(@RequestParam(required = false) String q,
                                    @PageableDefault(size = 20) Pageable pageable) {
        Page<Technology> page = technologyService.search(q, pageable);
        return page.map(TechnologyMapper.INSTANCE::toDto);
    }

    /**
     * Get technology by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<TechnologyDto> get(@PathVariable Long id) {
        Technology t = techRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(TechnologyMapper.INSTANCE.toDto(t));
    }

    /**
     * Update a technology
     */
    @PutMapping("/{id}")
    public ResponseEntity<TechnologyDto> update(@PathVariable Long id,
                                                @RequestBody TechnologyUpdateDto dto) {
        Technology t = technologyService.update(id, dto, "system");
        return ResponseEntity.ok(TechnologyMapper.INSTANCE.toDto(t));
    }

    /**
     * Soft delete a technology
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        technologyService.softDelete(id, "system");
    }
}
