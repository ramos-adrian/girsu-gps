package ar.ramos.girsugps.config;

import com.google.maps.GeoApiContext;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.WKTWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleMapsApiConfig {

    private final String API_KEY;

    public GoogleMapsApiConfig(@Value("${GOOGLE_MAPS_API}") String apiKey) {
        API_KEY = apiKey;
    }

    @Bean
    public GeoApiContext geoApiContext() {
        return new GeoApiContext.Builder().apiKey(API_KEY).build();
    }

    @Bean
    public GeometryFactory geometryFactory() {
        return new GeometryFactory();
    }

    @Bean
    public WKTWriter wktWriter() {
        return new WKTWriter();
    }
}
