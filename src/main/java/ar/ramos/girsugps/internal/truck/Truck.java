package ar.ramos.girsugps.internal.truck;

import ar.ramos.girsugps.internal.positionRecord.PositionRecord;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Entity
public class Truck {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String plate;
}
