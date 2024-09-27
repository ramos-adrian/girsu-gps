package ar.ramos.girsugps;

import ar.ramos.girsugps.internal.truck.Truck;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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

}
