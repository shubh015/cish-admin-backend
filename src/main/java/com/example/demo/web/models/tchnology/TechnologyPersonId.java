package com.example.demo.web.models.tchnology;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TechnologyPersonId implements Serializable {
    private Long technologyId;
    private Long personId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TechnologyPersonId)) return false;
        TechnologyPersonId that = (TechnologyPersonId) o;
        return Objects.equals(technologyId, that.technologyId) &&
               Objects.equals(personId, that.personId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(technologyId, personId);
    }
}