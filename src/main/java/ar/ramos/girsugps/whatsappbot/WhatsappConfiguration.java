package ar.ramos.girsugps.whatsappbot;

import com.whatsapp.api.WhatsappApiFactory;
import com.whatsapp.api.configuration.ApiVersion;
import com.whatsapp.api.impl.WhatsappBusinessCloudApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("whatsapp")
public class WhatsappConfiguration {

    @Bean
    public WhatsappBusinessCloudApi whatsappBusinessCloudApi(
            @Value("${WHATSAPP_TOKEN}") String token
    ) {
        WhatsappApiFactory factory = WhatsappApiFactory.newInstance(token);
        return factory.newBusinessCloudApi(ApiVersion.V19_0);
    }

}
