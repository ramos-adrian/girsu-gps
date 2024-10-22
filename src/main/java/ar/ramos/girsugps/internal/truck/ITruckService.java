package ar.ramos.girsugps.internal.truck;

import ar.ramos.girsugps.internal.positionrecord.PositionRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITruckService {
    Truck findById(Long id);
    Truck save(Truck truck);
    Page<Truck> findAll(Pageable pageable);
    Page<PositionRecord> findPositionRecordsByTruckId(Long truckId, Pageable pageable);
    void deleteById(Long id);
    boolean existsById(Long id);
}
