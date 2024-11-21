package ar.ramos.girsugps.internal.user;

import ar.ramos.girsugps.internal.place.Place;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class UserHome {
    @Id
    private long id;

    @ManyToOne
    private Place place;
    private long latitude;
    private long longitude;
}
