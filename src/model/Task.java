package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Representa una tarea del sistema. Es la pieza central del proyecto.
// Se guarda en el DiccionarioTareas (de Franco) y se usa en casi todo.
public class Task {

    private String id;            // identificador unico, ej: "TASK-001"
    private String title;         // titulo de la tarea
    private String description;   // descripcion detallada (puede quedar vacia)
    private String moduleId;      // a que modulo pertenece esta tarea
    private int priority;         // prioridad del 1 (baja) al 5 (alta)
    private TaskStatus status;    // estado actual (PENDING, IN_PROGRESS, BLOCKED, DONE)
    private String assignedTo;    // id del usuario asignado a esta tarea
    private LocalDate createdAt;  // fecha en que se creo la tarea
    private List<String> tags;    // etiquetas para clasificar la tarea, ej: ["backend", "urgente"]
    private List<Subtask> subtasks; // lista de subtareas que tiene esta tarea

    // Constructor: recibe los datos basicos. Los demas se inicializan con valores por defecto
    public Task(String id, String title, String moduleId, int priority) {
        this.id = id;
        this.title = title;
        this.moduleId = moduleId;
        this.priority = priority;
        this.description = "";              // descripcion vacia por defecto
        this.status = TaskStatus.PENDING;   // empieza como pendiente
        this.assignedTo = null;             // sin asignar al principio
        this.createdAt = LocalDate.now();   // fecha de hoy
        this.tags = new ArrayList<>();      // lista de tags vacia
        this.subtasks = new ArrayList<>();  // lista de subtareas vacia
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getModuleId() {
        return moduleId;
    }

    public int getPriority() {
        return priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    // Setters: para modificar los datos de la tarea despues de crearla
    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    @Override
    public String toString() {
        return "Tarea[" + id + "] " + title + " | prioridad: " + priority + " | estado: " + status;
    }
}
