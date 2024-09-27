package ar.ramos.girsugps.internal.truck;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/trucks")
public class TruckController {

    private final TruckRepository truckRepository;

    public TruckController(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }

    @GetMapping("/{id}")
    private ResponseEntity<Truck> findById(@PathVariable Long id) {
        Optional<Truck> truck = truckRepository.findById(id);

        if (truck.isPresent()) {
            return ResponseEntity.ok(truck.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
