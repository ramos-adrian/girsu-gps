package ar.ramos.girsugps;

import ar.ramos.girsugps.internal.positionRecord.PositionRecord;
import ar.ramos.girsugps.internal.truck.Truck;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = "/data.sql")
class GirsugpsApplicationTests {

    @Value("${ADMIN_USERNAME}")
    private String adminUsername;

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void truckFindById() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth(adminUsername, adminPassword)
                .getForEntity("/api/trucks/99", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());

        Number id = documentContext.read("$.id");
        assertThat(id).isNotNull();
        assertThat(id.intValue()).isEqualTo(99);

        String plate = documentContext.read("$.plate");
        assertThat(plate).isNotNull();
        assertThat(plate).isEqualTo("AA 123 BB");
    }

    @Test
    void truckFindByIdUnauthorized() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("sarah", "password")
                .getForEntity("/api/trucks/99", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void truckFindByIdNotFound() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth(adminUsername, adminPassword)
                .getForEntity("/api/trucks/999", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void createNewTruck() {
        Truck newTruck = new Truck();
        newTruck.setId(1921L);
        newTruck.setPlate("BB 123 BB");

        ResponseEntity<Truck> response = restTemplate
                .withBasicAuth(adminUsername, adminPassword)
                .postForEntity("/api/trucks", newTruck, Truck.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Truck responseBody = response.getBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getId()).isGreaterThan(0);
        assertThat(responseBody.getPlate()).isEqualTo("BB 123 BB");

        URI location = response.getHeaders().getLocation();

        System.out.println(location);

        ResponseEntity<String> responseGet = restTemplate
                .withBasicAuth(adminUsername, adminPassword)
                .getForEntity(location, String.class);

        assertThat(responseGet.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(responseGet.getBody());

        Number id = documentContext.read("$.id");
        assertThat(id).isNotNull();
        assertThat(id.intValue()).isGreaterThan(0);

        String plate = documentContext.read("$.plate");
        assertThat(plate).isNotNull();
        assertThat(plate).isEqualTo("BB 123 BB");
    }

    @Test
    void truckFindAll() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth(adminUsername, adminPassword)
                .getForEntity("/api/trucks?page=0&size=52", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());

        Number size = documentContext.read("$.size()");
        assertThat(size).isNotNull();
        assertThat(size.intValue()).isEqualTo(52);

        JSONArray ids = documentContext.read("$..id");
        assertThat(ids).isNotNull();
        assertThat(ids.size()).isEqualTo(52);
        assertThat(ids).containsExactlyInAnyOrder(
                99, 100, 101, 102, 103, 104, 105, 106, 107, 108,
                109, 110, 111, 112, 113, 114, 115, 116, 117, 118,
                119, 120, 121, 122, 123, 124, 125, 126, 127, 128,
                129, 130, 131, 132, 133, 134, 135, 136, 137, 138,
                139, 140, 141, 142, 143, 144, 145, 146, 147, 148,
                149, 150
        );
    }

    @Test
    void truckReturnPage() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth(adminUsername, adminPassword)
                .getForEntity("/api/trucks?page=0&size=10", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());

        Number size = documentContext.read("$.size()");
        assertThat(size).isNotNull();
        assertThat(size.intValue()).isEqualTo(10);

        JSONArray ids = documentContext.read("$..id");
        assertThat(ids).isNotNull();
        assertThat(ids.size()).isEqualTo(10);
        assertThat(ids).containsExactlyInAnyOrder(
                99, 100, 101, 102, 103, 104, 105, 106, 107, 108
        );
    }

    @Test
    void truckReturnSortedPage() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth(adminUsername, adminPassword)
                .getForEntity("/api/trucks?page=0&size=10&sort=plate,asc", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());

        Number size = documentContext.read("$.size()");
        assertThat(size).isNotNull();
        assertThat(size.intValue()).isEqualTo(10);

        JSONArray plates = documentContext.read("$..plate");
        assertThat(plates).isNotNull();
        assertThat(plates.size()).isEqualTo(10);
        assertThat(plates).containsExactly(
                "AA 123 BB",
                "AA 901 BB",
                "AB 012 CD",
                "AB 890 CD",
                "CC 234 DD",
                "CC 456 DD",
                "CD 123 EF",
                "CD 901 EF",
                "EE 567 FF",
                "EE 789 FF"
        );
    }

    @Test
    void positionRecordFindAll() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth(adminUsername, adminPassword)
                .getForEntity("/api/positionRecords?page=0&size=10", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());

        Number size = documentContext.read("$.size()");
        assertThat(size).isNotNull();
        assertThat(size.intValue()).isEqualTo(10);

        JSONArray ids = documentContext.read("$..id");
        assertThat(ids).isNotNull();
        assertThat(ids.size()).isEqualTo(10);
        assertThat(ids).containsExactlyInAnyOrder(
                48, 47, 46, 45, 44, 43, 42, 41, 40, 39
        );

        JSONArray timestamps = documentContext.read("$..timestamp");
        assertThat(timestamps).isNotNull();
        assertThat(timestamps.size()).isEqualTo(10);
        assertThat(timestamps).containsExactly(
                1695256800000L, 1695235200000L, 1695213600000L, 1695192000000L, 1695170400000L, 1695148800000L, 1695127200000L, 1695105600000L, 1695084000000L, 1695062400000L
        );
    }

    @Test
    void positionRecordSave() {

        long timestamp = System.currentTimeMillis();

        PositionRecord toSave = new PositionRecord();
        toSave.setTruckId(149L);
        toSave.setLatitude(-27.346002192793083);
        toSave.setLongitude(-65.60045678346874);
        toSave.setTimestamp(timestamp);

        ResponseEntity<PositionRecord> response = restTemplate
                .withBasicAuth(adminUsername, adminPassword)
                .postForEntity("/api/positionRecords", toSave, PositionRecord.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        PositionRecord responseBody = response.getBody();

        assertThat(responseBody).isNotNull();

        assertThat(responseBody.getId()).isGreaterThan(0);
        assertThat(responseBody.getTruckId()).isEqualTo(149);
        assertThat(responseBody.getTimestamp()).isEqualTo(timestamp);
        assertThat(responseBody.getLatitude()).isEqualTo(-27.346002192793083);
        assertThat(responseBody.getLongitude()).isEqualTo(-65.60045678346874);

        ResponseEntity<String> responseGet = restTemplate
                .withBasicAuth(adminUsername, adminPassword)
                .getForEntity("/api/positionRecords?page=0&size=1", String.class);

        assertThat(responseGet.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(responseGet.getBody());

        Number size = documentContext.read("$.size()");
        assertThat(size).isNotNull();
        assertThat(size.intValue()).isEqualTo(1);

        Object firstElement = documentContext.read("$[0]");
        assertThat(firstElement).isNotNull();

        Number id = documentContext.read("$[0].id");
        assertThat(id).isNotNull();
        assertThat(id.intValue()).isGreaterThan(0);

        Number truckId = documentContext.read("$[0].truckId");
        assertThat(truckId).isNotNull();
        assertThat(truckId.intValue()).isEqualTo(149);

        Number savedTimestamp = documentContext.read("$[0].timestamp");
        assertThat(savedTimestamp).isNotNull();
        assertThat(savedTimestamp.longValue()).isEqualTo(timestamp);

        Number latitude = documentContext.read("$[0].latitude");
        assertThat(latitude).isNotNull();
        assertThat(latitude.doubleValue()).isEqualTo(-27.346002192793083);

        Number longitude = documentContext.read("$[0].longitude");
        assertThat(longitude).isNotNull();
        assertThat(longitude.doubleValue()).isEqualTo(-65.60045678346874);
    }

    @Test
    void positionRecordsByTruckId() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth(adminUsername, adminPassword)
                .getForEntity("/api/trucks/99/positionRecords?page=0&size=10", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());

        Number size = documentContext.read("$.size()");
        assertThat(size).isNotNull();
        assertThat(size.intValue()).isEqualTo(3);

        JSONArray ids = documentContext.read("$..id");
        assertThat(ids).isNotNull();
        assertThat(ids.size()).isEqualTo(3);
        assertThat(ids).containsExactlyInAnyOrder(
                30, 31, 32
        );

        JSONArray timestamps = documentContext.read("$..timestamp");
        assertThat(timestamps).isNotNull();
        assertThat(timestamps.size()).isEqualTo(3);
        assertThat(timestamps).containsExactly(
                1694865600000L, 1694851200000L, 1694822400000L
        );
    }

    @Test
    @DirtiesContext
    void shouldDeleteAnExistingTruck() {
        ResponseEntity<Void> response = restTemplate
                .withBasicAuth(adminUsername, adminPassword)
                .exchange("/api/trucks/99", HttpMethod.DELETE, null, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> responseGet = restTemplate
                .withBasicAuth(adminUsername, adminPassword)
                .getForEntity("/api/trucks/99", String.class);

        assertThat(responseGet.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldNotDeleteAnUnexistingTruck() {
        ResponseEntity<Void> response = restTemplate
                .withBasicAuth(adminUsername, adminPassword)
                .exchange("/api/trucks/987452", HttpMethod.DELETE, null, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldNotDeleteAnExistingTruckUnauthorized() {
        ResponseEntity<Void> response = restTemplate
                .withBasicAuth("sarah", "password")
                .exchange("/api/trucks/99", HttpMethod.DELETE, null, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}
