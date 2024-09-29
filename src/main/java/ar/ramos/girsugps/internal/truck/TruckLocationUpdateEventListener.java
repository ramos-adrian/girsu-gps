package ar.ramos.girsugps.internal.truck;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class TruckLocationUpdateEventListener {

    private final SimpMessagingTemplate messagingTemplate;

    public TruckLocationUpdateEventListener(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleTruckLocationUpdate(TruckLocationUpdateDTO update) {
        messagingTemplate.convertAndSend("/topic/truckLocationUpdates", update);
    }
}