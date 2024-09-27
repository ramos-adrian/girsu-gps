package ar.ramos.girsugps.internal.truck;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Truck {
    @Id
    private Long id;
    private String plate;
}
