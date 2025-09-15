package com.example.demo.web.models.tchnology;



import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TechnologyUpdateDto {
    private String name;
    private String category;
    private String shortDescription;
    private Map<String, Object> techDetails;
    private String icNo;
    private Integer yearDevelopment;
    private Integer yearRelease;
    private Integer yearCommercialization;
    private String[] targetCustomers;
}
