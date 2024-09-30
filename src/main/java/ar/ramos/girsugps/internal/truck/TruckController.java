package ar.ramos.girsugps.internal.truck;

import ar.ramos.girsugps.internal.positionRecord.PositionRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @GetMapping
    private ResponseEntity<Iterable<Truck>> findAll(Pageable pageable) {
        Page<Truck> page = truckService.findAll(pageable);
        return ResponseEntity.ok(page.getContent());
    }

    @GetMapping("/{id}")
    private ResponseEntity<Truck> findById(@PathVariable Long id) {
        Truck truck = truckService.findById(id);
        return ResponseEntity.ok(truck);
    }

    @PostMapping
    private ResponseEntity<Truck> save(@RequestBody Truck truck, UriComponentsBuilder ucb) {
        Truck savedTruck = truckService.save(truck);
        URI location = ucb.path("/trucks/{id}").buildAndExpand(savedTruck.getId()).toUri();
        return ResponseEntity.created(location).body(savedTruck);
    }

    // Get positions of a truck
    @GetMapping("/{id}/positionRecords")
    private ResponseEntity<Iterable<PositionRecord>> findPositionRecords(@PathVariable Long id, Pageable pageable) {
        Page<PositionRecord> positionRecords = truckService.findPositionRecordsByTruckId(id, pageable);
        return ResponseEntity.ok(positionRecords.getContent());
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> delete(@PathVariable Long id) {
        if(truckService.existsById(id)){
            truckService.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
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
