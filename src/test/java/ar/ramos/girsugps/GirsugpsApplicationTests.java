package ar.ramos.girsugps;

import ar.ramos.girsugps.internal.truck.Truck;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GirsugpsApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void truckFindById() {
        ResponseEntity<String> response = restTemplate.getForEntity("/trucks/99", String.class);

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
    void truckFindByIdNotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity("/trucks/999", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void createNewTruck() {
        Truck newTruck = new Truck(null, "BB 123 BB");

        ResponseEntity<Void> response = restTemplate.postForEntity("/trucks", newTruck, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI location = response.getHeaders().getLocation();

        ResponseEntity<String> responseGet = restTemplate.getForEntity(location, String.class);

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
        ResponseEntity<String> response = restTemplate.getForEntity("/trucks?page=0&size=52", String.class);

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
        ResponseEntity<String> response = restTemplate.getForEntity("/trucks?page=0&size=10", String.class);

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
        ResponseEntity<String> response = restTemplate.getForEntity("/trucks?page=0&size=10&sort=plate,asc", String.class);

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

}
