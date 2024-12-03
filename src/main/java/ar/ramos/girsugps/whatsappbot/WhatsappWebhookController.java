package ar.ramos.girsugps.whatsappbot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.whatsapp.api.domain.webhook.WebHook;
import com.whatsapp.api.domain.webhook.WebHookEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Profile("whatsapp")
@RestController
@RequestMapping("/whatsappwebhook")
public class WhatsappWebhookController {

    @PostMapping
    public ResponseEntity<Void> update(@RequestBody String payload) throws JsonProcessingException {
        System.out.println("Received webhook event");
        WebHookEvent event = WebHook.constructEvent(payload);
        System.out.println(event);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<String> verificationRequest(@RequestParam("hub.mode") String mode,
                                                      @RequestParam("hub.challenge") String challenge,
                                                      @RequestParam("hub.verify_token") String verifyToken,
                                                      @Value("${WHATSAPP_WEBHOOK_VERIFY_TOKEN}") String expectedVerifyToken) {
        System.out.println("Received verification request");
        if (!expectedVerifyToken.equals(verifyToken)) {
            System.out.println("Invalid verify token " + verifyToken + " expected " + expectedVerifyToken);
            return ResponseEntity.badRequest().body("Invalid verify token");
        }
        System.out.println("Returning challenge " + challenge);
        return ResponseEntity.ok(challenge);
    }

}
