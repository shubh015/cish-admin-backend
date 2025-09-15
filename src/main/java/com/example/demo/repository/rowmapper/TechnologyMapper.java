package com.example.demo.repository.rowmapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.example.demo.web.models.tchnology.Technology;
import com.example.demo.web.models.tchnology.TechnologyCreateDto;
import com.example.demo.web.models.tchnology.TechnologyDto;
import com.example.demo.web.models.tchnology.TechnologyUpdateDto;

@Mapper(componentModel = "spring")
public interface TechnologyMapper {

    TechnologyMapper INSTANCE = Mappers.getMapper(TechnologyMapper.class);

    TechnologyDto toDto(Technology entity);

    Technology toEntity(TechnologyCreateDto dto);

    void updateEntityFromDto(TechnologyUpdateDto dto, @MappingTarget Technology entity);

     Technology toEntity(TechnologyDto dto);

}
    