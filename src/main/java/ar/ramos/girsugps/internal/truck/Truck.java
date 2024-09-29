package ar.ramos.girsugps.internal.truck;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
public class Truck {
    @Id
    private Long id;
    private String plate;
}
