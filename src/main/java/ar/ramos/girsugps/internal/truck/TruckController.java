package ar.ramos.girsugps.internal.truck;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/trucks")
public class TruckController {

    private final ITruckService truckService;

    public TruckController(ITruckService truckService) {
        this.truckService = truckService;
    }

    @GetMapping("/{id}")
    private ResponseEntity<Truck> findById(@PathVariable Long id) {
        Truck truck = truckService.findById(id);
        return ResponseEntity.ok(truck);
    }

    @PostMapping
    private ResponseEntity<Void> save(@RequestBody Truck truck, UriComponentsBuilder ucb) {
        Truck savedTruck = truckService.save(truck);
        URI location = ucb.path("trucks/{id}").buildAndExpand(savedTruck.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    // TODO Move out of the class
    @ControllerAdvice
    public static class GlobalExceptionHandler {

        @ExceptionHandler(TruckService.EntityNotFoundException.class)
        public ResponseEntity<String> handleEntityNotFound(TruckService.EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found");
        }
    }
}
