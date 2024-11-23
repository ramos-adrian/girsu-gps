package ar.ramos.girsugps.internal.routepoint;

public interface IRoutePointService {
    RoutePoint findNearestRoutePoint(double lat, double lng);
}
