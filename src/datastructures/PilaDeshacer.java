/*
 Objetos de model que vas a usar:
 - model.Task       -> la tarea cuyo estado vas a guardar antes de modificarla
 - model.TaskStatus -> el enum con los estados posibles (PENDING, IN_PROGRESS, BLOCKED, DONE)
   (importar con: import model.Task; import model.TaskStatus;)

 Campos de Task que te van a ser utiles:
 - String id
 - String title
 - String description
 - int priority
 - TaskStatus status
 - String assignedTo
 - List<String> tags

 Implementacion esperada:
 - pila LIFO sobre array o lista enlazada simple

 Clase interna esperada:
 - InstantaneaTarea
   - String taskId
   - String title
   - String description
   - int priority
   - TaskStatus status
   - String assignedTo
   - List<String> tags
   - LocalDateTime momentoInstantanea

 Metodos publicos esperados:
 - void apilar(Task tarea)
 - InstantaneaTarea desapilar()
 - InstantaneaTarea verTope()
 - boolean estaVacia()
 - int tamanio()
 - void imprimirHistorial()

 Regla importante:
 - apilar guarda el estado actual antes de modificar la tarea
*/

package datastructures;

import model.Task;
import model.TaskStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PilaDeshacer {
    private Nodo tope;
    private int tamanio;

    // LISTA ENLAZADA SIMPLE
    private static class Nodo {
        InstantaneaTarea dato;
        Nodo siguiente;

        Nodo(InstantaneaTarea dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }

    public static class InstantaneaTarea {
        private String taskId;
        private String title;
        private String description;
        private int priority;
        private TaskStatus status;
        private String assignedTo;
        private List<String> tags;
        private LocalDateTime momentoInstantanea;

        public InstantaneaTarea(Task tarea) {
            this.taskId = tarea.getId();
            this.title = tarea.getTitle();
            this.description = tarea.getDescription();
            this.priority = tarea.getPriority();
            this.status = tarea.getStatus();
            this.assignedTo = tarea.getAssignedTo();
            this.tags = tarea.getTags() != null ? new ArrayList<>(tarea.getTags()) : new ArrayList<>();
            this.momentoInstantanea = LocalDateTime.now();
        }

        // GETTERS
        public String getTaskId() { return taskId; }
        public String getTitle() { return title; }
        public String getDescription() { return description; }
        public int getPriority() { return priority; }
        public TaskStatus getStatus() { return status; }
        public String getAssignedTo() { return assignedTo; }
        public List<String> getTags() { return tags; }
        public LocalDateTime getMomentoInstantanea() { return momentoInstantanea; }

        @Override
        public String toString() {
            return "[" + momentoInstantanea + "] Tarea ID: " + taskId +
                    " | Título: '" + title + "' | Estado: " + status +
                    " | Prioridad: " + priority + " | Asignado a: " + assignedTo +
                    " | Etiquetas: " + tags;
        }
    }

    public PilaDeshacer() {
        this.tope = null;
        this.tamanio = 0;
    }

    public void apilar(Task tarea) {
        if (tarea == null) return;
        InstantaneaTarea nuevaInstantanea = new InstantaneaTarea(tarea);
        Nodo nuevoNodo = new Nodo(nuevaInstantanea);

        nuevoNodo.siguiente = tope;
        tope = nuevoNodo;
        tamanio++;
    }

    public InstantaneaTarea desapilar() {
        if (estaVacia()) return null;
        InstantaneaTarea dato = tope.dato;
        tope = tope.siguiente;
        tamanio--;
        return dato;
    }

    public InstantaneaTarea verTope() {
        if (estaVacia()) return null;
        return tope.dato;
    }

    public boolean estaVacia() {
        return tope == null;
    }

    public int tamanio() {
        return tamanio;
    }

    public void imprimirHistorial() {
        if (estaVacia()) {
            System.out.println("No hay registros en el historial para deshacer.");
            return;
        }
        Nodo actual = tope;
        System.out.println("=== HISTORIAL DE DESHACER (LIFO) ===");
        while (actual != null) {
            System.out.println(actual.dato);
            actual = actual.siguiente;
        }
        System.out.println("=====================================");
    }
}
