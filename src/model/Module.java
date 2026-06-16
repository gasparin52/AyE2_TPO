package model;

import java.util.ArrayList;
import java.util.List;

// Representa un modulo dentro de un proyecto.
// Un modulo agrupa varias tareas relacionadas.
// Es el nivel del medio: Proyecto -> Modulo -> Tarea
public class Module {

    private String id;          // identificador, ej: "MOD-001"
    private String name;        // nombre del modulo, ej: "Autenticacion", "Pagos"
    private String projectId;   // a que proyecto pertenece este modulo
    private List<Task> tasks;   // lista de tareas que tiene este modulo

    // Constructor: la lista de tareas empieza vacia
    public Module(String id, String name, String projectId) {
        this.id = id;
        this.name = name;
        this.projectId = projectId;
        this.tasks = new ArrayList<>(); // sin tareas al principio
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProjectId() {
        return projectId;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    // Agrega una tarea a este modulo
    public void agregarTarea(Task tarea) {
        tasks.add(tarea);
    }

    @Override
    public String toString() {
        return "Modulo[" + id + "] " + name + " | tareas: " + tasks.size();
    }
}
