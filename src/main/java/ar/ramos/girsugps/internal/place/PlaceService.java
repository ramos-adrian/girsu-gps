package ar.ramos.girsugps.internal.place;

import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.RoadsApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
import com.google.maps.model.SnappedPoint;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PlaceService {

    private final GeoApiContext geoApiContext;

    public PlaceService(GeoApiContext geoApiContext) {
        this.geoApiContext = geoApiContext;
    }

    public String getPlaceIdFromLatLng(LatLng latLng) {
        SnappedPoint[] snappedPoints = new SnappedPoint[0];
        try {
            snappedPoints = RoadsApi.nearestRoads(geoApiContext, latLng).await();
        } catch (ApiException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return snappedPoints[0].placeId;
    }

}
