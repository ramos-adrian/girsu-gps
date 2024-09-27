package ar.ramos.girsugps.internal.truck;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITruckService {
    Truck findById(Long id);
    Truck save(Truck truck);
    Page<Truck> findAll(Pageable pageable);
}
