package com.example.demo.web.models.tchnology;



import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDto {
    private String fullName;
    private String email;
    private String affiliation;
}
