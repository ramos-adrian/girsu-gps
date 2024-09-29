package ar.ramos.girsugps.internal.truck;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TruckLocationUpdateDTO {
    private Long id;
    private Long truckId;
    private Double latitude;
    private Double longitude;
    private Long timestamp;
}
