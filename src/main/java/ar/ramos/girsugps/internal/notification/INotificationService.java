package ar.ramos.girsugps.internal.notification;

import ar.ramos.girsugps.internal.user.UserHome;

public interface INotificationService {
    public void sendNotification(UserHome userHome, String message);
}
