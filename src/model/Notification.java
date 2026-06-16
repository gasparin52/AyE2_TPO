package model;

import java.time.LocalDateTime;

// Representa una notificacion que se le manda a un usuario
// Se guarda en la ColaNotificaciones
public class Notification {

    private String message;           // el texto de la notificacion
    private String targetUserId;      // el id del usuario que la recibe
    private LocalDateTime timestamp;  // cuando se creo la notificacion

    // Constructor: cuando creamos una notificacion, el timestamp se asigna solo
    public Notification(String message, String targetUserId) {
        this.message = message;
        this.targetUserId = targetUserId;
        this.timestamp = LocalDateTime.now(); // guarda la hora actual automaticamente
    }

    // Getters
    public String getMessage() {
        return message;
    }

    public String getTargetUserId() {
        return targetUserId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "[" + timestamp + "] Para " + targetUserId + ": " + message;
    }
}
