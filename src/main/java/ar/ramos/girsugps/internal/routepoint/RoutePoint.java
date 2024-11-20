package ar.ramos.girsugps.internal.routepoint;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"lat", "lng"}))
public class RoutePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double lat;
    private double lng;

    private String placeId;
}