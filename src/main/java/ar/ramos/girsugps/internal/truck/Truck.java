package ar.ramos.girsugps.internal.truck;

import ar.ramos.girsugps.internal.routepoint.RoutePoint;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Entity
public class Truck {
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String plate;

    @OneToMany
    @OrderColumn
    private List<RoutePoint> route;
}
