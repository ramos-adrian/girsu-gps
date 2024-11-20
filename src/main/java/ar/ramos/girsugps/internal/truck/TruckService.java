package ar.ramos.girsugps.internal.truck;

import ar.ramos.girsugps.internal.positionrecord.IPositionRecordRepository;
import ar.ramos.girsugps.internal.positionrecord.PositionRecord;
import ar.ramos.girsugps.internal.routepoint.RoutePoint;
import ar.ramos.girsugps.internal.routepoint.RoutePointRepository;
import com.google.maps.GeoApiContext;
import com.google.maps.RoadsApi;
import com.google.maps.SnapToRoadsApiRequest;
import com.google.maps.model.LatLng;
import com.google.maps.model.SnappedPoint;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TruckService implements ITruckService {

    private final ITruckRepository truckRepository;
    private final IPositionRecordRepository positionRecordRepository;
    private final RoutePointRepository routePointRepository;
    private final GeoApiContext geoApiContext;
    private final GeometryFactory geometryFactory;

    public TruckService(
            ITruckRepository truckRepository,
            IPositionRecordRepository positionRecordRepository,
            RoutePointRepository routePointRepository,
            GeoApiContext geoApiContext,
            GeometryFactory geometryFactory
    ) {
        this.truckRepository = truckRepository;
        this.positionRecordRepository = positionRecordRepository;
        this.routePointRepository = routePointRepository;
        this.geoApiContext = geoApiContext;
        this.geometryFactory = geometryFactory;
    }

    @Override
    public Truck findById(Long id) {
        return truckRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Truck not found with id: " + id));
    }

    @Override
    public Truck save(Truck truck) {
        return truckRepository.save(truck);
    }

    @Override
    public Page<Truck> findAll(Pageable pageable) {
        return truckRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "id"))
                )
        );
    }

    @Override
    public Page<PositionRecord> findPositionRecordsByTruckId(Long truckId, Pageable pageable) {
        return positionRecordRepository.findPositionRecordsByTruckId(
                truckId,
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.DESC, "timestamp"))
                )
        );
    }

    @Override
    public void deleteById(Long id) {
        truckRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return truckRepository.existsById(id);
    }

    @Override
    public List<RoutePoint> updateRoute(Long id, LatLng[] route) {
        Truck truck = findById(id);
        Optional<SnappedPoint[]> snappedPoints = getSnappedPoints(route);
        if (snappedPoints.isEmpty()) {
            throw new RuntimeException("Error getting snapped points");
        }
        List<RoutePoint> newRoute = getRouteFromSnappedPoints(snappedPoints.get());

        truck.getRoute().clear();
        truckRepository.save(truck);
        truck.setRoute(newRoute);
        truckRepository.save(truck);
        return newRoute;
    }

    private List<RoutePoint> getRouteFromSnappedPoints(SnappedPoint[] snappedPoints) {
        List<RoutePoint> newRoute = new ArrayList<>();
        for (SnappedPoint snappedPoint : snappedPoints) {
            Optional<RoutePoint> existingRoutePoint = routePointRepository.findByLatAndLng(snappedPoint.location.lat, snappedPoint.location.lng);
            if (existingRoutePoint.isPresent()) {
                if (newRoute.contains(existingRoutePoint.get()))continue;
                newRoute.add(existingRoutePoint.get());
                continue;
            }
            RoutePoint routePoint = new RoutePoint(
                    null,
                    snappedPoint.location.lat,
                    snappedPoint.location.lng,
                    snappedPoint.placeId
            );
            RoutePoint savedRoutePoint = routePointRepository.save(routePoint);
            newRoute.add(savedRoutePoint);
        }
        return newRoute;
    }

    private Optional<SnappedPoint[]> getSnappedPoints(LatLng[] route) {
        SnapToRoadsApiRequest snapToRoadsApiRequest = RoadsApi.snapToRoads(geoApiContext, true, route);
        SnappedPoint[] snappedPoints = null;
        try {
            snappedPoints = snapToRoadsApiRequest.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(snappedPoints);
    }


    public static class EntityNotFoundException extends RuntimeException {
        public EntityNotFoundException(String message) {
            super(message);
        }
    }
}
