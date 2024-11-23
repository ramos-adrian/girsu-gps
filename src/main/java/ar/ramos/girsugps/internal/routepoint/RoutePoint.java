package ar.ramos.girsugps.internal.routepoint;

import ar.ramos.girsugps.internal.place.Place;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@ToString
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"lat", "lng"}))
public class RoutePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double lat;
    private double lng;

    @ManyToOne
    private Place place;
}