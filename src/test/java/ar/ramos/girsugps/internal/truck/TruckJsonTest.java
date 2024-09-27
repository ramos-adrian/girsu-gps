package ar.ramos.girsugps.internal.truck;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
public class TruckJsonTest {

    @Autowired
    private JacksonTester<Truck> jacksonTester;

    @Test
    public void truckSerializationTest() throws IOException {

        Truck truck = new Truck(1L, "AA 123 BB");

        assertThat(jacksonTester.write(truck)).isEqualToJson("truck.json");

        assertThat(jacksonTester.write(truck)).hasJsonPathNumberValue("@.id");
        assertThat(jacksonTester.write(truck)).extractingJsonPathNumberValue("@.id").isEqualTo(1);

        assertThat(jacksonTester.write(truck)).hasJsonPathStringValue("@.plate");
        assertThat(jacksonTester.write(truck)).extractingJsonPathStringValue("@.plate").isEqualTo("AA 123 BB");

    }

    @Test
    public void truckDeserializationTest() throws IOException {

        String content = "{\"id\":1,\"plate\":\"AA 123 BB\"}";

        Truck truck = new Truck(1L, "AA 123 BB");

        assertThat(jacksonTester.parseObject(content).getId()).isEqualTo(1);
        assertThat(jacksonTester.parseObject(content).getPlate()).isEqualTo("AA 123 BB");

    }
}
