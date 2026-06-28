package service;

import model.Subtask;
import model.Task;

public class DemoDataLoader {

    private DemoDataLoader() {
    }

    public static void cargar(ProjectService projectService, WorkPanelService workPanelService, IndexService indexService) {
        projectService.createProject("PROJ-001", "Gestor Academico", "TP integrador de estructuras.");
        projectService.assignMember("USR-001");
        projectService.assignMember("USR-002");

        projectService.addModule("PROJ-001", "MOD-001", "Backend");
        projectService.addModule("PROJ-001", "MOD-002", "Frontend");

        Task t1 = crearTareaDemo("TASK-001", "Disenar modelo", "MOD-001", 5, "USR-001", "arquitectura", "backend");
        Task t2 = crearTareaDemo("TASK-002", "Implementar login", "MOD-001", 4, "USR-001", "backend", "feature");
        Task t3 = crearTareaDemo("TASK-003", "Armar tablero", "MOD-002", 3, "USR-002", "frontend", "ux");
        Task t4 = crearTareaDemo("TASK-004", "Agregar reportes", "MOD-002", 2, "USR-002", "reportes", "feature");
        Task t5 = crearTareaDemo("TASK-005", "Corregir bug de estado", "MOD-001", 5, "USR-001", "bug", "backend");

        registrarTarea(projectService, workPanelService, indexService, "MOD-001", t1);
        registrarTarea(projectService, workPanelService, indexService, "MOD-001", t2);
        registrarTarea(projectService, workPanelService, indexService, "MOD-002", t3);
        registrarTarea(projectService, workPanelService, indexService, "MOD-002", t4);
        registrarTarea(projectService, workPanelService, indexService, "MOD-001", t5);

        projectService.addSubtask("TASK-002", new Subtask("SUB-001", "Disenar endpoint", "TASK-002", 3));
        projectService.addSubtask("TASK-002", new Subtask("SUB-002", "Implementar JWT", "TASK-002", 4));
        projectService.addSubtask("TASK-003", new Subtask("SUB-003", "Dibujar mockup", "TASK-003", 2));

        projectService.addDependency("TASK-001", "TASK-002");
        projectService.addDependency("TASK-002", "TASK-005");
        projectService.addDependency("TASK-003", "TASK-004");

        indexService.logAction("Carga inicial", "TASK-001", "system");
        indexService.logAction("Carga inicial", "TASK-002", "system");
        indexService.logAction("Carga inicial", "TASK-003", "system");
        indexService.logAction("Carga inicial", "TASK-004", "system");
        indexService.logAction("Carga inicial", "TASK-005", "system");
    }

    private static Task crearTareaDemo(String id, String titulo, String modulo, int prioridad, String asignado, String... tags) {
        Task tarea = new Task(id, titulo, modulo, prioridad);
        tarea.setAssignedTo(asignado);
        tarea.setDescription("Descripcion de " + titulo);
        for (String tag : tags) {
            tarea.getTags().add(tag);
        }
        return tarea;
    }

    private static void registrarTarea(ProjectService projectService, WorkPanelService workPanelService,
                                       IndexService indexService, String moduloId, Task tarea) {
        projectService.addTask(moduloId, tarea);
        workPanelService.addTaskToPanel(tarea);
        indexService.indexTask(tarea);
    }
}
