package datastructures;

import model.Task;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DiccionarioTareas {

    private HashMap<String, Task> mapaTareas;
    private HashSet<String> miembrosProyecto;

    public DiccionarioTareas() {
        mapaTareas = new HashMap<>();
        miembrosProyecto = new HashSet<>();
    }

    // --- Metodos de tareas ---

    public void agregarTarea(Task tarea) {
        mapaTareas.put(tarea.getId(), tarea);
    }

    public Task obtenerTarea(String taskId) {
        return mapaTareas.get(taskId);
    }

    public Task obtener(String taskId) {
        return obtenerTarea(taskId);
    }

    public void eliminarTarea(String taskId) {
        mapaTareas.remove(taskId);
    }

    public boolean contieneTarea(String taskId) {
        return mapaTareas.containsKey(taskId);
    }

    public Collection<Task> obtenerTodasLasTareas() {
        return mapaTareas.values();
    }

    // --- Metodos de miembros ---

    public void agregarMiembro(String userId) {
        miembrosProyecto.add(userId);
    }

    public boolean esMiembro(String userId) {
        return miembrosProyecto.contains(userId);
    }

    public void eliminarMiembro(String userId) {
        miembrosProyecto.remove(userId);
    }

    public Set<String> obtenerTodosLosMiembros() {
        return miembrosProyecto;
    }

    // --- Metodo especial de tags ---

    public void agregarTagATarea(String taskId, String tag) {
        Task tarea = mapaTareas.get(taskId);
        if (tarea == null) return;

        // Evita duplicados aunque tags sea una List
        if (!tarea.getTags().contains(tag)) {
            tarea.getTags().add(tag);
        }
    }
}
