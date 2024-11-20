package ar.ramos.girsugps.internal.routepoint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoutePointRepository extends JpaRepository<RoutePoint, Long> {
    Optional<RoutePoint> findByLatAndLng(double lat, double lng);
}