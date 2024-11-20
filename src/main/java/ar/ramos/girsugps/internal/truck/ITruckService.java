package ar.ramos.girsugps.internal.truck;

import ar.ramos.girsugps.internal.positionrecord.PositionRecord;
import ar.ramos.girsugps.internal.routepoint.RoutePoint;
import com.google.maps.model.LatLng;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITruckService {
    Truck findById(Long id);
    Truck save(Truck truck);
    Page<Truck> findAll(Pageable pageable);
    Page<PositionRecord> findPositionRecordsByTruckId(Long truckId, Pageable pageable);
    void deleteById(Long id);
    boolean existsById(Long id);
    List<RoutePoint> updateRoute(Long id, LatLng[] route);
}
