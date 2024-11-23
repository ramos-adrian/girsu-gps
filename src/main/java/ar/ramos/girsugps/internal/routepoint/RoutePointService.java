package ar.ramos.girsugps.internal.routepoint;

public class RoutePointService implements IRoutePointService{

    private IRoutePointRepository routePointRepository;

    public RoutePointService(IRoutePointRepository routePointRepository) {
        this.routePointRepository = routePointRepository;
    }

    @Override
    public RoutePoint findNearestRoutePoint(double lat, double lng) {
        return null;
    }
}
