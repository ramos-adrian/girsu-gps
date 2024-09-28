package ar.ramos.girsugps.internal.positionRecord;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class PositionRecord {
    @Id
    private Long id;

    private Long truckId;
    private double latitude;
    private double longitude;
    private long timestamp; // in milliseconds since epoch
}
