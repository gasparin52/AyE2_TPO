package service;

import datastructures.ArbolABB;
import datastructures.ArbolAVL;
import datastructures.ArbolB;
import model.LogEntry;
import model.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IndexService {

    private ArbolAVL indiceBacklog;
    private ArbolABB indiceTags;
    private ArbolB indiceLogs;
    private HashMap<String, Task> tareasIndexadas;
    private int logCounter;

    public IndexService() {
        this.indiceBacklog = new ArbolAVL();
        this.indiceTags = new ArbolABB();
        this.indiceLogs = new ArbolB();
        this.tareasIndexadas = new HashMap<>();
        this.logCounter = 1;
    }

    public void indexTask(Task task) {
        tareasIndexadas.put(task.getId(), task);
        reconstruirIndices();
    }

    public void removeTaskFromIndex(Task task) {
        if (task == null) {
            return;
        }
        tareasIndexadas.remove(task.getId());
        reconstruirIndices();
    }

    public List<Object> getTasksByDateRange(LocalDate from, LocalDate to) {
        List<Object> crudos = indiceBacklog.buscarPorRango(from, to);
        List<Object> resultado = new ArrayList<>();

        for (Object item : crudos) {
            if (item instanceof List) {
                resultado.addAll((List<?>) item);
            } else {
                resultado.add(item);
            }
        }

        return resultado;
    }

    public List<String> getTasksByTag(String tag) {
        return indiceTags.buscar(tag);
    }

    public void logAction(String action, String taskId, String userId) {
        LogEntry entrada = new LogEntry(String.format("LOG-%03d", logCounter++), action, taskId, userId);
        indiceLogs.insertar(entrada.getTimestamp().toString(), entrada);
    }

    public void printBacklog() {
        System.out.println("=== BACKLOG POR FECHA ===");
        indiceBacklog.imprimirEnOrden();
        System.out.println("Altura AVL: " + indiceBacklog.mostrarAltura() + " | Balance raiz: " + indiceBacklog.factorEquilibrio());
    }

    public void printTagIndex() {
        indiceTags.imprimirEnOrden();
    }

    public void printAuditLog() {
        indiceLogs.imprimirEnOrden();
    }

    private void reconstruirIndices() {
        indiceBacklog = new ArbolAVL();
        indiceTags = new ArbolABB();
        HashMap<LocalDate, List<Task>> tareasPorFecha = new HashMap<>();

        for (Task tarea : tareasIndexadas.values()) {
            tareasPorFecha.computeIfAbsent(tarea.getCreatedAt(), ignored -> new ArrayList<>()).add(tarea);
            for (String tag : tarea.getTags()) {
                indiceTags.insertar(tag, tarea.getId());
            }
        }

        for (LocalDate fecha : tareasPorFecha.keySet()) {
            indiceBacklog.insertar(fecha, tareasPorFecha.get(fecha));
        }
    }
}
