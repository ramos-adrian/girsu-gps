package ar.ramos.girsugps.whatsappbot;

import ar.ramos.girsugps.internal.auth.AuthenticationService;
import ar.ramos.girsugps.internal.auth.RegisterRequest;
import ar.ramos.girsugps.internal.place.PlaceService;
import ar.ramos.girsugps.internal.user.User;
import ar.ramos.girsugps.internal.user.UserHome;
import ar.ramos.girsugps.internal.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.maps.model.LatLng;
import com.whatsapp.api.domain.messages.BodyComponent;
import com.whatsapp.api.domain.messages.Language;
import com.whatsapp.api.domain.messages.TemplateMessage;
import com.whatsapp.api.domain.messages.TextParameter;
import com.whatsapp.api.domain.messages.type.ComponentType;
import com.whatsapp.api.domain.messages.type.MessageType;
import com.whatsapp.api.domain.templates.type.LanguageType;
import com.whatsapp.api.domain.webhook.*;
import com.whatsapp.api.domain.webhook.type.FieldType;
import com.whatsapp.api.impl.WhatsappBusinessCloudApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.System;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Profile("whatsapp")
@RestController
@RequestMapping("/whatsappwebhook")
public class WhatsappWebhookController {

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final PlaceService placeService;
    private final WhatsappBusinessCloudApi wpApi;
    private final String WHATSAPP_PHONE_ID;

    public WhatsappWebhookController(
            AuthenticationService authenticationService,
            UserService userService,
            PlaceService placeService,
            WhatsappBusinessCloudApi wpApi,
            @Value("${WHATSAPP_PHONE_ID}") String whatsappPhoneId) {
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.placeService = placeService;
        this.wpApi = wpApi;
        this.WHATSAPP_PHONE_ID = whatsappPhoneId;
    }

    @PostMapping
    public ResponseEntity<Void> update(@RequestBody String payload) throws JsonProcessingException {
        System.out.println("Received webhook event");
        WebHookEvent event = WebHook.constructEvent(payload);
        System.out.println(event);
        String object = event.object();

        if (!object.equals("whatsapp_business_account")) {
            System.out.println("Invalid object " + object);
            return ResponseEntity.badRequest().build();
        }

        CompletableFuture.runAsync(() -> registerHome(event));


        return ResponseEntity.ok().build();
    }

    private void registerHome(WebHookEvent event) {
        List<Entry> entryList = event.entry();
        for (Entry entry : entryList) {
            List<Change> changes = entry.changes();
            for (Change change : changes) {
                FieldType field = change.field();
                if (!field.equals(FieldType.MESSAGES)) continue;
                com.whatsapp.api.domain.webhook.Value value = change.value();
                if (!value.messagingProduct().equals("whatsapp")) continue;
                List<Message> messages = value.messages();
                for (Message message : messages) {
                    if (!message.type().equals(MessageType.LOCATION)) continue;

                    System.out.println("Received location message");

                    Location location = message.location();
                    double lat = location.latitude();
                    double lng = location.longitude();
                    System.out.println("Lat: " + lat + " Lng: " + lng);

                    String username = message.from();
                    User user = (User) userService.loadUserByUsername(username);

                    if (user == null) {
                        try {
                            String password = message.from() + message.timestamp();
                            RegisterRequest registerRequest = new RegisterRequest(username, password);
                            user = (User) authenticationService.register(registerRequest);
                        } catch (RuntimeException e) {
                            System.out.println("Failed to register user " + username);
                            break;
                        }
                    }

                    String placeId = placeService.getPlaceIdFromLatLng(new LatLng(lat, lng));
                    UserHome userHome = new UserHome();
                    userHome.setLatitude(lat);
                    userHome.setLongitude(lng);
                    userHome.setPlaceId(placeId);
                    userService.setHome(username, userHome);

                    System.out.println("User " + username + " registered home at " + lat + ", " + lng);

                    System.out.println("Sending confirmation message");
                    com.whatsapp.api.domain.messages.Message msg = com.whatsapp.api.domain.messages.Message.MessageBuilder
                            .builder()
                            .setTo(username)
                            .buildTemplateMessage(
                                    new TemplateMessage()
                                            .setLanguage(new Language(LanguageType.ES_AR))
                                            .setName("garbage_truck_is_near") // TODO change to the correct template name
                            );

                    wpApi.sendMessage(WHATSAPP_PHONE_ID, msg);
                }
            }
        }
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
