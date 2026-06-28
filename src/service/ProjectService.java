package service;

import datastructures.ArbolNArio;
import datastructures.DiccionarioTareas;
import datastructures.GrafoDirigido;
import model.Module;
import model.Project;
import model.Subtask;
import model.Task;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ProjectService {

    private ArbolNArio<Object> arbolProyecto;
    private GrafoDirigido grafoDependencias;
    private DiccionarioTareas diccionarioTareas;
    private Project proyectoActual;
    private HashMap<String, Module> modulosPorId;

    public ProjectService() {
        this.diccionarioTareas = new DiccionarioTareas();
        this.arbolProyecto = new ArbolNArio<>();
        this.grafoDependencias = new GrafoDirigido(diccionarioTareas);
        this.modulosPorId = new HashMap<>();
    }

    public void createProject(String id, String name, String description) {
        proyectoActual = new Project(id, name, description);
        arbolProyecto.setRaiz(proyectoActual);
        modulosPorId.clear();
    }

    public void addModule(String projectId, String moduleId, String moduleName) {
        validarProyecto(projectId);
        Module modulo = new Module(moduleId, moduleName, projectId);
        proyectoActual.agregarModulo(modulo);
        modulosPorId.put(moduleId, modulo);
        arbolProyecto.insertar(proyectoActual, modulo);
    }

    public void addTask(String moduleId, Task task) {
        Module modulo = modulosPorId.get(moduleId);
        if (modulo == null) {
            throw new IllegalArgumentException("No existe el modulo " + moduleId);
        }

        modulo.agregarTarea(task);
        diccionarioTareas.agregarTarea(task);
        grafoDependencias.agregarVertice(task.getId());
        arbolProyecto.insertar(modulo, task);
    }

    public void addSubtask(String taskId, Subtask subtask) {
        Task tarea = findTask(taskId);
        if (tarea == null) {
            throw new IllegalArgumentException("No existe la tarea " + taskId);
        }

        tarea.agregarSubtarea(subtask);
        arbolProyecto.insertar(tarea, subtask);
    }

    public void addDependency(String fromId, String toId) {
        if (!diccionarioTareas.contieneTarea(fromId) || !diccionarioTareas.contieneTarea(toId)) {
            throw new IllegalArgumentException("Las dependencias solo pueden darse entre tareas existentes.");
        }
        grafoDependencias.agregarDependencia(fromId, toId);
    }

    public void assignMember(String userId) {
        if (proyectoActual == null) {
            throw new IllegalStateException("Primero hay que crear un proyecto.");
        }
        proyectoActual.agregarMiembro(userId);
        diccionarioTareas.agregarMiembro(userId);
    }

    public Task findTask(String taskId) {
        return diccionarioTareas.obtenerTarea(taskId);
    }

    public void printProjectHierarchy() {
        arbolProyecto.imprimirArbol();
    }

    public void printBlockedTasks() {
        System.out.println("=== TAREAS BLOQUEADAS ===");
        boolean hayBloqueadas = false;

        for (Task tarea : diccionarioTareas.obtenerTodasLasTareas()) {
            if (grafoDependencias.estaBloqueada(tarea.getId())) {
                hayBloqueadas = true;
                System.out.println(tarea.getId() + " <- bloqueada por " + grafoDependencias.obtenerBloqueantesDe(tarea.getId()));
            }
        }

        if (!hayBloqueadas) {
            System.out.println("No hay tareas bloqueadas.");
        }
    }

    public Project getProyectoActual() {
        return proyectoActual;
    }

    public Collection<Task> getAllTasks() {
        return diccionarioTareas.obtenerTodasLasTareas();
    }

    public DiccionarioTareas getTaskDictionary() {
        return diccionarioTareas;
    }

    public GrafoDirigido getDependencyGraph() {
        return grafoDependencias;
    }

    public ArbolNArio<Object> getProjectTree() {
        return arbolProyecto;
    }

    public Map<String, Module> getModulesById() {
        return modulosPorId;
    }

    private void validarProyecto(String projectId) {
        if (proyectoActual == null || !proyectoActual.getId().equals(projectId)) {
            throw new IllegalArgumentException("No existe el proyecto " + projectId);
        }
    }
}
