package ar.ramos.girsugps.internal.positionRecord;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
public class PositionRecord {
    @Id
    private Long id;

    private Long truckId;
    private double latitude;
    private double longitude;
    private long timestamp; // in milliseconds since epoch
}
