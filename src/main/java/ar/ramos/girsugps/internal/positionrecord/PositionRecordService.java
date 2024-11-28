package ar.ramos.girsugps.internal.positionrecord;

import ar.ramos.girsugps.internal.notification.INotificationService;
import ar.ramos.girsugps.internal.routepoint.RoutePoint;
import ar.ramos.girsugps.internal.truck.ITruckRepository;
import ar.ramos.girsugps.internal.truck.Truck;
import ar.ramos.girsugps.internal.truck.TruckLocationUpdateDTO;
import ar.ramos.girsugps.internal.user.IUserHomeRepository;
import ar.ramos.girsugps.internal.user.UserHome;
import com.google.maps.model.LatLng;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PositionRecordService implements IPositionRecordService {

    private final IPositionRecordRepository positionRecordRepository;
    private final ITruckRepository truckRepository;
    private final IUserHomeRepository userHomeRepository;
    private final INotificationService notificationService;
    private final ApplicationEventPublisher eventPublisher;

    public PositionRecordService(IPositionRecordRepository positionRecordRepository,
                                 ITruckRepository truckRepository,
                                 ApplicationEventPublisher eventPublisher,
                                 INotificationService notificationService,
                                 IUserHomeRepository userHomeRepository) {
        this.positionRecordRepository = positionRecordRepository;
        this.truckRepository = truckRepository;
        this.userHomeRepository = userHomeRepository;
        this.notificationService = notificationService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Page<PositionRecord> findAll(Pageable pageable) {
        return positionRecordRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Order.desc("timestamp")))
                )
        );
    }

    @Override
    public PositionRecord save(PositionRecord positionRecord) {

        Optional<Truck> optTruck = truckRepository.findById(positionRecord.getTruckId());
        if (optTruck.isEmpty()) {
            throw new IllegalArgumentException("Truck not found");
        }
        PositionRecord savedRecord = positionRecordRepository.save(positionRecord);
        publishNewPositionEvent(savedRecord);

        Truck truck = optTruck.get();
        List<RoutePoint> routePoints = truck.getRoute();
        if (routePoints.isEmpty()) {
            throw new IllegalArgumentException("Truck has no route");
        }
        RoutePoint snappedTruckRoutePoint = null;
        double minDistance = Double.MAX_VALUE;

        for (RoutePoint routePoint : routePoints) {
            double distance = haversine(
                    savedRecord.getLatitude(), savedRecord.getLongitude(),
                    routePoint.getLat(), routePoint.getLng()
            );
            // TODO Remove Magic Number 50
            if (distance < 50 && distance < minDistance) {
                minDistance = distance;
                snappedTruckRoutePoint = routePoint;
            }
        }


        if (snappedTruckRoutePoint == null) {
            return savedRecord;
        }

        List<RoutePoint> nextRoutePoints = getNextRoutePoints(routePoints, snappedTruckRoutePoint, 500);

        for (RoutePoint routePoint : nextRoutePoints) {
            List<UserHome> nearbyHomes = getNearbyHomes(routePoint, 500);
            for (UserHome home : nearbyHomes) {
                if (home.getLastNotified() == 0 || home.getLastNotified() < System.currentTimeMillis() - 5 * 1000 ) {
                    home.setLastNotified(System.currentTimeMillis());
                    userHomeRepository.save(home);
                    System.out.println("Sending notification to home with id: " + home.getId());
                    notificationService.sendNotification(home, "El camión de basura pasará por tu casa en 15 minutos");
                }
            }
        }

        return savedRecord;
    }

    // TODO Apply memoization
    // TODO check that the name of the street is the same
    private List<UserHome> getNearbyHomes(RoutePoint routePoint, int maxDistance) {
        List<UserHome> nearbyHomes = new ArrayList<>();
        List<UserHome> allHomes = userHomeRepository.findAll();
        for (UserHome home : allHomes) {
            double distance = haversine(routePoint.getLat(), routePoint.getLng(), home.getLatitude(), home.getLongitude());
            if (distance < maxDistance) {
                nearbyHomes.add(home);
            }
        }
        return nearbyHomes;
    }

    // TODO Cache nextRoute points.
    private List<RoutePoint> getNextRoutePoints(List<RoutePoint> routePoints, RoutePoint snappedTruckRoutePoint, final int maxDistance) {
        double distance = 0;
        List<RoutePoint> nextRoutePoints = new ArrayList<>();
        nextRoutePoints.add(snappedTruckRoutePoint);

        int i = routePoints.indexOf(snappedTruckRoutePoint) + 1;
        while (distance < maxDistance && i < routePoints.size() - 1) {
            RoutePoint current = routePoints.get(i);
            RoutePoint next = routePoints.get(i + 1);
            distance += haversine(current.getLat(), current.getLng(), next.getLat(), next.getLng());
            nextRoutePoints.add(next);
            i++;
        }
        return nextRoutePoints;
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6378; // Radius of the earth in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c * 1000; // convert to meters
    }

    private void publishNewPositionEvent(PositionRecord savedRecord) {
        TruckLocationUpdateDTO update = new TruckLocationUpdateDTO(
                savedRecord.getId(),
                savedRecord.getTruckId(),
                savedRecord.getLatitude(),
                savedRecord.getLongitude(),
                savedRecord.getTimestamp()
        );
        eventPublisher.publishEvent(update);
    }
}
