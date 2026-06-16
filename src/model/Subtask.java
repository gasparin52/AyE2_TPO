package model;

// Representa una subtarea: es una tarea mas chica dentro de una tarea principal.
// Se usa en el ArbolNArio como tercer nivel (Proyecto -> Modulo -> Tarea -> Subtarea).
// Tambien sirve para calcular el progreso de una tarea.
public class Subtask {

    private String id;         // identificador, ej: "SUB-001"
    private String title;      // nombre de la subtarea
    private String taskId;     // a que tarea principal pertenece
    private boolean completed; // true si esta completada, false si no
    private int priority;      // prioridad del 1 (baja) al 5 (alta)

    // Constructor: por defecto la subtarea empieza sin completar
    public Subtask(String id, String title, String taskId, int priority) {
        this.id = id;
        this.title = title;
        this.taskId = taskId;
        this.priority = priority;
        this.completed = false; // arranca como no completada
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTaskId() {
        return taskId;
    }

    public boolean isCompleted() {
        return completed;
    }

    public int getPriority() {
        return priority;
    }

    // Setter: permite marcar la subtarea como completada (o revertirla)
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        String estado = completed ? "✓ completada" : "pendiente";
        return "Subtarea[" + id + "] " + title + " - " + estado;
    }
}
