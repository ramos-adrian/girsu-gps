package ar.ramos.girsugps.internal.positionrecord;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Entity
public class PositionRecord {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long truckId;
    private double latitude;
    private double longitude;
    private long timestamp; // in milliseconds since epoch
}
