package ar.ramos.girsugps.internal.routepoint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRoutePointRepository extends JpaRepository<RoutePoint, Long> {
    Optional<RoutePoint> findByLatAndLng(double lat, double lng);

    @Query(value = "SELECT lat, lng, pl.street_address, pl.street_number FROM route_point rp " +
            "JOIN truck_route tr ON rp.id = tr.route_id " +
            "JOIN place pl ON rp.place_place_id = pl.place_id " +
            "WHERE tr.truck_id = :truckId " +
            "ORDER BY (6371 * acos(cos(radians(:lat)) * cos(radians(rp.lat)) * cos(radians(rp.lng) - radians(:lng)) + sin(radians(:lat)) * sin(radians(rp.lat)))) ASC " +
            "LIMIT 1", nativeQuery = true)
    List<Object[]> findNearestPlaceIdForTruck(@Param("lat") double lat, @Param("lng") double lng, @Param("truckId") Long truckId);

    @Query(value = "SELECT uh.* FROM user_home uh " +
            "WHERE (6371 * acos(cos(radians(:lat)) * cos(radians(uh.latitude)) * cos(radians(uh.longitude) - radians(:lng)) + sin(radians(:lat)) * sin(radians(uh.latitude)))) < (:radius / 1000.0) " +
            "ORDER BY (6371 * acos(cos(radians(:lat)) * cos(radians(uh.latitude)) * cos(radians(uh.longitude) - radians(:lng)) + sin(radians(:lat)) * sin(radians(uh.latitude)))) ASC " +
            "LIMIT 1", nativeQuery = true)
    List<Object[]> findNearestUserHome(@Param("lat") double lat, @Param("lng") double lng, @Param("radius") double radius);
}