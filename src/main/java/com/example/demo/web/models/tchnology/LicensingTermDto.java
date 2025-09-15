package com.example.demo.web.models.tchnology;


import java.math.BigDecimal;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LicensingTermDto {
    private String natureOfLicense;
    private Integer durationYears;
    private String territory;
    private Long licenseFeeCents;
    private BigDecimal rebatePercent;

    private String royalty;
    private String notes;
}
