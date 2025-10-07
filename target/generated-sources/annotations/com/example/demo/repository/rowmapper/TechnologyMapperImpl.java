package com.example.demo.repository.rowmapper;

import com.example.demo.web.models.tchnology.LicensingTerm;
import com.example.demo.web.models.tchnology.LicensingTermDto;
import com.example.demo.web.models.tchnology.Technology;
import com.example.demo.web.models.tchnology.TechnologyCreateDto;
import com.example.demo.web.models.tchnology.TechnologyDto;
import com.example.demo.web.models.tchnology.TechnologyUpdateDto;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-08T03:34:40+0530",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251001-1143, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class TechnologyMapperImpl implements TechnologyMapper {

    @Override
    public TechnologyDto toDto(Technology entity) {
        if ( entity == null ) {
            return null;
        }

        TechnologyDto.TechnologyDtoBuilder technologyDto = TechnologyDto.builder();

        technologyDto.category( entity.getCategory() );
        technologyDto.icNo( entity.getIcNo() );
        technologyDto.id( entity.getId() );
        technologyDto.isActive( entity.getIsActive() );
        technologyDto.name( entity.getName() );
        technologyDto.shortDescription( entity.getShortDescription() );
        technologyDto.targetCustomers( stringListToStringArray( entity.getTargetCustomers() ) );
        Map<String, Object> map = entity.getTechDetails();
        if ( map != null ) {
            technologyDto.techDetails( new LinkedHashMap<String, Object>( map ) );
        }
        technologyDto.yearCommercialization( entity.getYearCommercialization() );
        technologyDto.yearDevelopment( entity.getYearDevelopment() );
        technologyDto.yearRelease( entity.getYearRelease() );

        return technologyDto.build();
    }

    @Override
    public Technology toEntity(TechnologyCreateDto dto) {
        if ( dto == null ) {
            return null;
        }

        Technology.TechnologyBuilder technology = Technology.builder();

        technology.category( dto.getCategory() );
        technology.icNo( dto.getIcNo() );
        technology.licensingTerms( licensingTermDtoListToLicensingTermList( dto.getLicensingTerms() ) );
        technology.name( dto.getName() );
        technology.shortDescription( dto.getShortDescription() );
        List<String> list1 = dto.getTargetCustomers();
        if ( list1 != null ) {
            technology.targetCustomers( new ArrayList<String>( list1 ) );
        }
        Map<String, Object> map = dto.getTechDetails();
        if ( map != null ) {
            technology.techDetails( new LinkedHashMap<String, Object>( map ) );
        }
        technology.yearCommercialization( dto.getYearCommercialization() );
        technology.yearDevelopment( dto.getYearDevelopment() );
        technology.yearRelease( dto.getYearRelease() );

        return technology.build();
    }

    @Override
    public void updateEntityFromDto(TechnologyUpdateDto dto, Technology entity) {
        if ( dto == null ) {
            return;
        }

        entity.setCategory( dto.getCategory() );
        entity.setIcNo( dto.getIcNo() );
        entity.setName( dto.getName() );
        entity.setShortDescription( dto.getShortDescription() );
        if ( entity.getTargetCustomers() != null ) {
            List<String> list = stringArrayToStringList( dto.getTargetCustomers() );
            if ( list != null ) {
                entity.getTargetCustomers().clear();
                entity.getTargetCustomers().addAll( list );
            }
            else {
                entity.setTargetCustomers( null );
            }
        }
        else {
            List<String> list = stringArrayToStringList( dto.getTargetCustomers() );
            if ( list != null ) {
                entity.setTargetCustomers( list );
            }
        }
        if ( entity.getTechDetails() != null ) {
            Map<String, Object> map = dto.getTechDetails();
            if ( map != null ) {
                entity.getTechDetails().clear();
                entity.getTechDetails().putAll( map );
            }
            else {
                entity.setTechDetails( null );
            }
        }
        else {
            Map<String, Object> map = dto.getTechDetails();
            if ( map != null ) {
                entity.setTechDetails( new LinkedHashMap<String, Object>( map ) );
            }
        }
        entity.setYearCommercialization( dto.getYearCommercialization() );
        entity.setYearDevelopment( dto.getYearDevelopment() );
        entity.setYearRelease( dto.getYearRelease() );
    }

    @Override
    public Technology toEntity(TechnologyDto dto) {
        if ( dto == null ) {
            return null;
        }

        Technology.TechnologyBuilder technology = Technology.builder();

        technology.category( dto.getCategory() );
        technology.icNo( dto.getIcNo() );
        technology.id( dto.getId() );
        technology.isActive( dto.getIsActive() );
        technology.name( dto.getName() );
        technology.shortDescription( dto.getShortDescription() );
        technology.targetCustomers( stringArrayToStringList( dto.getTargetCustomers() ) );
        Map<String, Object> map = dto.getTechDetails();
        if ( map != null ) {
            technology.techDetails( new LinkedHashMap<String, Object>( map ) );
        }
        technology.yearCommercialization( dto.getYearCommercialization() );
        technology.yearDevelopment( dto.getYearDevelopment() );
        technology.yearRelease( dto.getYearRelease() );

        return technology.build();
    }

    protected String[] stringListToStringArray(List<String> list) {
        if ( list == null ) {
            return null;
        }

        String[] stringTmp = new String[list.size()];
        int i = 0;
        for ( String string : list ) {
            stringTmp[i] = string;
            i++;
        }

        return stringTmp;
    }

    protected LicensingTerm licensingTermDtoToLicensingTerm(LicensingTermDto licensingTermDto) {
        if ( licensingTermDto == null ) {
            return null;
        }

        LicensingTerm.LicensingTermBuilder licensingTerm = LicensingTerm.builder();

        licensingTerm.durationYears( licensingTermDto.getDurationYears() );
        licensingTerm.licenseFeeCents( licensingTermDto.getLicenseFeeCents() );
        licensingTerm.natureOfLicense( licensingTermDto.getNatureOfLicense() );
        licensingTerm.notes( licensingTermDto.getNotes() );
        licensingTerm.rebatePercent( licensingTermDto.getRebatePercent() );
        licensingTerm.royalty( licensingTermDto.getRoyalty() );
        licensingTerm.territory( licensingTermDto.getTerritory() );

        return licensingTerm.build();
    }

    protected List<LicensingTerm> licensingTermDtoListToLicensingTermList(List<LicensingTermDto> list) {
        if ( list == null ) {
            return null;
        }

        List<LicensingTerm> list1 = new ArrayList<LicensingTerm>( list.size() );
        for ( LicensingTermDto licensingTermDto : list ) {
            list1.add( licensingTermDtoToLicensingTerm( licensingTermDto ) );
        }

        return list1;
    }

    protected List<String> stringArrayToStringList(String[] stringArray) {
        if ( stringArray == null ) {
            return null;
        }

        List<String> list = new ArrayList<String>( stringArray.length );
        for ( String string : stringArray ) {
            list.add( string );
        }

        return list;
    }
}
