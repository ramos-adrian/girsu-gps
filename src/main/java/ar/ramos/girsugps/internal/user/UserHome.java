package ar.ramos.girsugps.internal.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class UserHome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String placeId;
    private double latitude;
    private double longitude;

    @Data
    public static class UserHomeDTO {
        private final String username;
        private final double latitude;
        private final double longitude;
    }
}
