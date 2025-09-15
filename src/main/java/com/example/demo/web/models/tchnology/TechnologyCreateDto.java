package com.example.demo.web.models.tchnology;


import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TechnologyCreateDto {
    private String name;
    private String category;
    private String shortDescription;
    private Map<String, Object> techDetails;
    private String icNo;
    private Integer yearDevelopment;
    private Integer yearRelease;
    private Integer yearCommercialization;
    private List<String> targetCustomers;
    private List<PersonDto> inventors;
    private List<LicensingTermDto> licensingTerms;
}
