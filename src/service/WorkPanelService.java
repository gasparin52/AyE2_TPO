package service;

import datastructures.ColaNotificaciones;
import datastructures.DiccionarioTareas;
import datastructures.MonticuloMaximo;
import datastructures.PilaDeshacer;
import model.Notification;
import model.Task;
import model.TaskStatus;

import java.util.List;

public class WorkPanelService {

    private MonticuloMaximo panelTrabajo;
    private PilaDeshacer pilaDeshacer;
    private ColaNotificaciones colaNotificaciones;

    public WorkPanelService() {
        this.panelTrabajo = new MonticuloMaximo();
        this.pilaDeshacer = new PilaDeshacer();
        this.colaNotificaciones = new ColaNotificaciones();
    }

    public void addTaskToPanel(Task task) {
        panelTrabajo.insertar(task);
    }

    public Task getNextTask() {
        return panelTrabajo.extraerMaximo();
    }

    public void modifyTask(Task task, String nuevaDescripcion, TaskStatus nuevoEstado, Integer nuevaPrioridad,
                           String nuevoAsignado, List<String> nuevosTags) {
        if (task == null) {
            throw new IllegalArgumentException("La tarea no puede ser null.");
        }

        pilaDeshacer.apilar(task);

        if (nuevaDescripcion != null) {
            task.setDescription(nuevaDescripcion);
        }
        if (nuevoEstado != null) {
            task.setStatus(nuevoEstado);
        }
        if (nuevoAsignado != null) {
            task.setAssignedTo(nuevoAsignado);
        }
        if (nuevaPrioridad != null) {
            panelTrabajo.actualizarPrioridad(task.getId(), nuevaPrioridad);
        }
        if (nuevosTags != null) {
            task.setTags(nuevosTags);
        }

        sendNotification("Se actualizo la tarea " + task.getId(), task.getAssignedTo());
    }

    public void undoLastAction(DiccionarioTareas dict) {
        PilaDeshacer.InstantaneaTarea snapshot = pilaDeshacer.desapilar();
        if (snapshot == null) {
            System.out.println("No hay cambios para deshacer.");
            return;
        }

        Task tarea = dict.obtenerTarea(snapshot.getTaskId());
        if (tarea == null) {
            System.out.println("No se encontro la tarea del historial: " + snapshot.getTaskId());
            return;
        }

        tarea.setTitle(snapshot.getTitle());
        tarea.setDescription(snapshot.getDescription());
        tarea.setPriority(snapshot.getPriority());
        tarea.setStatus(snapshot.getStatus());
        tarea.setAssignedTo(snapshot.getAssignedTo());
        tarea.setTags(snapshot.getTags());
        panelTrabajo.actualizarPrioridad(tarea.getId(), tarea.getPriority());

        sendNotification("Se deshizo el ultimo cambio de " + tarea.getId(), tarea.getAssignedTo());
    }

    public void sendNotification(String message, String userId) {
        colaNotificaciones.encolar(new Notification(message, userId == null ? "sin-asignar" : userId));
    }

    public void processNextNotification() {
        Notification notification = colaNotificaciones.desencolar();
        if (notification != null) {
            System.out.println("Procesada: " + notification);
        }
    }

    public void showWorkPanel() {
        panelTrabajo.imprimirPanel();
    }

    public void showPendingNotifications() {
        colaNotificaciones.imprimirTodas();
    }

    public void showUndoHistory() {
        pilaDeshacer.imprimirHistorial();
    }

    public Task peekNextTask() {
        return panelTrabajo.verMaximo();
    }
}
