package model;

import java.time.LocalDateTime;

// Representa un registro de auditoria: guarda quien hizo que y cuando.
// Se usa para llevar un historial de acciones en el sistema.
// Se almacena en el ArbolB.
public class LogEntry {

    private String id;                // identificador del registro, ej: "LOG-001"
    private String action;            // que se hizo, ej: "crear tarea", "eliminar tarea"
    private String taskId;            // sobre que tarea se hizo la accion
    private String userId;            // quien realizo la accion
    private LocalDateTime timestamp;  // cuando ocurrio

    // Constructor: el timestamp se asigna automaticamente
    public LogEntry(String id, String action, String taskId, String userId) {
        this.id = id;
        this.action = action;
        this.taskId = taskId;
        this.userId = userId;
        this.timestamp = LocalDateTime.now(); // guarda la hora actual
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getAction() {
        return action;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + action + " | tarea: " + taskId + " | usuario: " + userId + " | " + timestamp;
    }
}
