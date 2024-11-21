package ar.ramos.girsugps.internal.place;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Entity
public class Place {
    @Id
    private String placeId;

    private String streetAddress;
    private String streetNumber;
}