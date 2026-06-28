import model.Module;
import model.Subtask;
import model.Task;
import model.TaskStatus;
import service.DemoDataLoader;
import service.IndexService;
import service.ProjectService;
import service.WorkPanelService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final ProjectService projectService = new ProjectService();
    private static final WorkPanelService workPanelService = new WorkPanelService();
    private static final IndexService indexService = new IndexService();

    public static void main(String[] args) {
        try {
            DemoDataLoader.cargar(projectService, workPanelService, indexService);
        } catch (Exception e) {
            System.out.println("No se pudieron cargar los datos iniciales: " + e.getMessage());
        }
        ejecutarMenu();
    }

    private static void ejecutarMenu() {
        boolean seguir = true;

        while (seguir) {
            System.out.println("\n=== GESTOR DE TAREAS ===");
            System.out.println("1. Gestion de proyecto");
            System.out.println("2. Panel de trabajo");
            System.out.println("3. Backlog e indices");
            System.out.println("4. Consultas complejas");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");

            String opcion = SCANNER.nextLine();
            switch (opcion) {
                case "1":
                    ejecutarAccionSegura(Main::menuProyecto);
                    break;
                case "2":
                    ejecutarAccionSegura(Main::menuTrabajo);
                    break;
                case "3":
                    ejecutarAccionSegura(Main::menuIndices);
                    break;
                case "4":
                    ejecutarAccionSegura(Main::menuConsultas);
                    break;
                case "0":
                    seguir = false;
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

    private static void menuProyecto() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n=== GESTION DE PROYECTO ===");
            System.out.println("1. Ver jerarquia");
            System.out.println("2. Alta de tarea");
            System.out.println("3. Alta de subtarea");
            System.out.println("4. Agregar dependencia");
            System.out.println("5. Ver tareas bloqueadas");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");

            String opcion = SCANNER.nextLine();
            switch (opcion) {
                case "1":
                    ejecutarAccionSegura(projectService::printProjectHierarchy);
                    break;
                case "2":
                    ejecutarAccionSegura(Main::crearTareaPorConsola);
                    break;
                case "3":
                    ejecutarAccionSegura(Main::crearSubtareaPorConsola);
                    break;
                case "4":
                    ejecutarAccionSegura(Main::agregarDependenciaPorConsola);
                    break;
                case "5":
                    ejecutarAccionSegura(projectService::printBlockedTasks);
                    break;
                case "0":
                    volver = true;
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

    private static void menuTrabajo() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n=== PANEL DE TRABAJO ===");
            System.out.println("1. Ver panel");
            System.out.println("2. Tomar siguiente tarea");
            System.out.println("3. Modificar tarea");
            System.out.println("4. Deshacer ultimo cambio");
            System.out.println("5. Ver notificaciones");
            System.out.println("6. Procesar siguiente notificacion");
            System.out.println("7. Ver historial undo");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");

            String opcion = SCANNER.nextLine();
            switch (opcion) {
                case "1":
                    ejecutarAccionSegura(workPanelService::showWorkPanel);
                    break;
                case "2":
                    ejecutarAccionSegura(() -> {
                        Task siguiente = workPanelService.getNextTask();
                        System.out.println(siguiente == null ? "No hay tareas en el panel." : "Siguiente tarea: " + siguiente);
                    });
                    break;
                case "3":
                    ejecutarAccionSegura(Main::modificarTareaPorConsola);
                    break;
                case "4":
                    ejecutarAccionSegura(() -> workPanelService.undoLastAction(projectService.getTaskDictionary()));
                    break;
                case "5":
                    ejecutarAccionSegura(workPanelService::showPendingNotifications);
                    break;
                case "6":
                    ejecutarAccionSegura(workPanelService::processNextNotification);
                    break;
                case "7":
                    ejecutarAccionSegura(workPanelService::showUndoHistory);
                    break;
                case "0":
                    volver = true;
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

    private static void menuIndices() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n=== BACKLOG E INDICES ===");
            System.out.println("1. Ver backlog por fecha");
            System.out.println("2. Ver indice de tags");
            System.out.println("3. Ver auditoria");
            System.out.println("4. Buscar tareas por tag");
            System.out.println("5. Buscar tareas por rango de fechas");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");

            String opcion = SCANNER.nextLine();
            switch (opcion) {
                case "1":
                    ejecutarAccionSegura(indexService::printBacklog);
                    break;
                case "2":
                    ejecutarAccionSegura(indexService::printTagIndex);
                    break;
                case "3":
                    ejecutarAccionSegura(indexService::printAuditLog);
                    break;
                case "4":
                    ejecutarAccionSegura(() -> {
                        System.out.print("Tag a buscar: ");
                        String tag = SCANNER.nextLine();
                        System.out.println("Tareas con tag " + tag + ": " + indexService.getTasksByTag(tag));
                    });
                    break;
                case "5":
                    ejecutarAccionSegura(() -> {
                        LocalDate desde = leerFecha("Fecha desde (AAAA-MM-DD): ");
                        LocalDate hasta = leerFecha("Fecha hasta (AAAA-MM-DD): ");
                        System.out.println("Resultado: " + indexService.getTasksByDateRange(desde, hasta));
                    });
                    break;
                case "0":
                    volver = true;
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

    private static void menuConsultas() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n=== CONSULTAS COMPLEJAS ===");
            System.out.println("1. Ver tareas bloqueadas con su posicion en la jerarquia");
            System.out.println("2. Tomar tarea mas prioritaria, ponerla en progreso y loguear");
            System.out.println("3. Deshacer ultimo cambio y reindexar");
            System.out.println("4. Recorrer proyecto y buscar por tag");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");

            String opcion = SCANNER.nextLine();
            switch (opcion) {
                case "1":
                    ejecutarAccionSegura(Main::consultaBloqueadas);
                    break;
                case "2":
                    ejecutarAccionSegura(Main::consultaTomarTareaPrioritaria);
                    break;
                case "3":
                    ejecutarAccionSegura(Main::consultaDeshacerYReindexar);
                    break;
                case "4":
                    ejecutarAccionSegura(Main::consultaRecorridoYTag);
                    break;
                case "0":
                    volver = true;
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

    private static void crearTareaPorConsola() {
        String moduleId = seleccionarModulo();
        String taskId = generarTaskId();
        System.out.println("ID asignado: " + taskId);
        System.out.print("Titulo: ");
        String titulo = SCANNER.nextLine();
        int prioridad = leerEnteroEnRango("Prioridad (1-5): ", 1, 5);

        Task tarea = new Task(taskId, titulo, moduleId, prioridad);
        System.out.print("Descripcion: ");
        tarea.setDescription(SCANNER.nextLine());
        System.out.print("Asignado a: ");
        tarea.setAssignedTo(SCANNER.nextLine());

        System.out.print("Tags separados por coma: ");
        String[] tags = SCANNER.nextLine().split(",");
        for (String tag : tags) {
            String limpio = tag.trim();
            if (!limpio.isEmpty()) {
                tarea.getTags().add(limpio);
            }
        }

        projectService.addTask(moduleId, tarea);
        workPanelService.addTaskToPanel(tarea);
        indexService.indexTask(tarea);
        System.out.println("Tarea creada.");
    }

    private static String seleccionarModulo() {
        List<Module> modulos = new ArrayList<>(projectService.getModulesById().values());
        modulos.sort(Comparator.comparing(Module::getId));

        if (modulos.isEmpty()) {
            throw new IllegalStateException("No hay modulos cargados en el proyecto.");
        }

        System.out.println("Modulos disponibles:");
        for (int i = 0; i < modulos.size(); i++) {
            Module modulo = modulos.get(i);
            System.out.println((i + 1) + ". " + modulo.getName() + " (" + modulo.getId() + ")");
        }

        while (true) {
            System.out.print("Elegi modulo por numero: ");
            String entrada = SCANNER.nextLine().trim();

            if (entrada.isBlank()) {
                System.out.println("Tenes que elegir un modulo.");
                continue;
            }

            try {
                int opcion = Integer.parseInt(entrada);
                if (opcion >= 1 && opcion <= modulos.size()) {
                    return modulos.get(opcion - 1).getId();
                }
                System.out.println("Opcion invalida. Elegi un numero de la lista.");
            } catch (NumberFormatException ignored) {
                System.out.println("Tenes que ingresar solo el numero del modulo.");
            }
        }
    }

    private static void crearSubtareaPorConsola() {
        Task tareaPadre = seleccionarTarea("Elegi la tarea a la que queres agregarle una subtarea:");
        String taskId = tareaPadre.getId();
        String subtaskId = generarSubtaskId();
        System.out.println("ID de subtarea asignado: " + subtaskId);
        System.out.print("Titulo: ");
        String titulo = SCANNER.nextLine();
        int prioridad = leerEnteroEnRango("Prioridad (1-5): ", 1, 5);

        projectService.addSubtask(taskId, new Subtask(subtaskId, titulo, taskId, prioridad));
        indexService.logAction("Alta de subtarea", taskId, "user-demo");
        System.out.println("Subtarea creada.");
    }

    private static void agregarDependenciaPorConsola() {
        Task origen = seleccionarTarea("Elegi la tarea origen (la que bloquea):");
        Task destino = seleccionarTareaDistinta("Elegi la tarea destino (la que queda bloqueada):", origen.getId());
        String fromId = origen.getId();
        String toId = destino.getId();

        projectService.addDependency(fromId, toId);
        indexService.logAction("Alta de dependencia", toId, "user-demo");
        System.out.println("Dependencia agregada.");
    }

    private static void modificarTareaPorConsola() {
        Task tarea = seleccionarTarea("Elegi la tarea que queres modificar:");

        System.out.print("Nueva descripcion (enter para dejar igual): ");
        String descripcion = SCANNER.nextLine();
        descripcion = descripcion.isBlank() ? null : descripcion;

        TaskStatus estado = leerEstadoOpcional();
        Integer prioridad = leerEnteroOpcionalEnRango("Nueva prioridad (enter para dejar igual): ", 1, 5);

        System.out.print("Nuevo asignado (enter para dejar igual): ");
        String asignado = SCANNER.nextLine();
        asignado = asignado.isBlank() ? null : asignado;

        System.out.print("Nuevos tags separados por coma (enter para dejar igual): ");
        String tagsTexto = SCANNER.nextLine();
        List<String> nuevosTags = null;
        if (!tagsTexto.isBlank()) {
            nuevosTags = new ArrayList<>();
            for (String tag : tagsTexto.split(",")) {
                String limpio = tag.trim();
                if (!limpio.isEmpty()) {
                    nuevosTags.add(limpio);
                }
            }
        }

        workPanelService.modifyTask(tarea, descripcion, estado, prioridad, asignado, nuevosTags);
        indexService.indexTask(tarea);
        indexService.logAction("Modificacion de tarea", tarea.getId(), tarea.getAssignedTo());
        System.out.println("Tarea actualizada.");
    }

    private static void consultaBloqueadas() {
        projectService.printProjectHierarchy();
        projectService.printBlockedTasks();
    }

    private static void consultaTomarTareaPrioritaria() {
        Task tarea = workPanelService.getNextTask();
        if (tarea == null) {
            System.out.println("No hay tareas en el panel.");
            return;
        }

        workPanelService.modifyTask(tarea, null, TaskStatus.IN_PROGRESS, null, tarea.getAssignedTo(), null);
        indexService.indexTask(tarea);
        indexService.logAction("Toma de tarea prioritaria", tarea.getId(), tarea.getAssignedTo());
        System.out.println("Tarea tomada: " + tarea);
    }

    private static void consultaDeshacerYReindexar() {
        workPanelService.undoLastAction(projectService.getTaskDictionary());
        for (Task tarea : projectService.getAllTasks()) {
            indexService.indexTask(tarea);
        }
        indexService.logAction("Deshacer y reindexar", "GLOBAL", "user-demo");
        System.out.println("Se deshizo el ultimo cambio y se actualizaron los indices.");
    }

    private static void consultaRecorridoYTag() {
        System.out.println("Recorrido por amplitud del proyecto:");
        System.out.println(projectService.getProjectTree().recorridoAmplitud());
        System.out.print("Tag a consultar: ");
        String tag = SCANNER.nextLine();
        System.out.println("Tareas encontradas: " + indexService.getTasksByTag(tag));
    }

    private static void ejecutarAccionSegura(Runnable accion) {
        try {
            accion.run();
        } catch (IllegalArgumentException e) {
            System.out.println("Dato invalido: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("Operacion no permitida: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocurrio un error inesperado. El programa sigue activo.");
            System.out.println("Detalle: " + e.getMessage());
        }
    }

    private static int leerEnteroEnRango(String mensaje, int minimo, int maximo) {
        while (true) {
            System.out.print(mensaje);
            String texto = SCANNER.nextLine();
            try {
                int valor = Integer.parseInt(texto);
                if (valor < minimo || valor > maximo) {
                    System.out.println("El numero debe estar entre " + minimo + " y " + maximo + ".");
                    continue;
                }
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Tenes que ingresar un numero valido.");
            }
        }
    }

    private static Integer leerEnteroOpcionalEnRango(String mensaje, int minimo, int maximo) {
        while (true) {
            System.out.print(mensaje);
            String texto = SCANNER.nextLine();
            if (texto.isBlank()) {
                return null;
            }
            try {
                int valor = Integer.parseInt(texto);
                if (valor < minimo || valor > maximo) {
                    System.out.println("El numero debe estar entre " + minimo + " y " + maximo + ".");
                    continue;
                }
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Tenes que ingresar un numero valido.");
            }
        }
    }

    private static LocalDate leerFecha(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = SCANNER.nextLine();
            try {
                return LocalDate.parse(texto);
            } catch (DateTimeParseException e) {
                System.out.println("La fecha tiene que estar en formato AAAA-MM-DD.");
            }
        }
    }

    private static TaskStatus leerEstadoOpcional() {
        while (true) {
            System.out.println("Nuevo estado:");
            System.out.println("1. PENDING");
            System.out.println("2. IN_PROGRESS");
            System.out.println("3. BLOCKED");
            System.out.println("4. DONE");
            System.out.print("Elegi un numero (enter para dejar igual): ");
            String estadoTexto = SCANNER.nextLine();
            if (estadoTexto.isBlank()) {
                return null;
            }
            try {
                int opcion = Integer.parseInt(estadoTexto.trim());
                switch (opcion) {
                    case 1:
                        return TaskStatus.PENDING;
                    case 2:
                        return TaskStatus.IN_PROGRESS;
                    case 3:
                        return TaskStatus.BLOCKED;
                    case 4:
                        return TaskStatus.DONE;
                    default:
                        System.out.println("Estado invalido. Elegi una opcion del 1 al 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Tenes que ingresar solo el numero del estado.");
            }
        }
    }

    private static Task seleccionarTarea(String mensaje) {
        List<Task> tareas = new ArrayList<>(projectService.getAllTasks());
        tareas.sort(Comparator.comparing(Task::getId));

        if (tareas.isEmpty()) {
            throw new IllegalStateException("No hay tareas cargadas.");
        }

        System.out.println(mensaje);
        for (int i = 0; i < tareas.size(); i++) {
            Task tarea = tareas.get(i);
            System.out.println((i + 1) + ". " + tarea.getId() + " - " + tarea.getTitle());
        }

        int opcion = leerEnteroEnRango("Elegi una tarea: ", 1, tareas.size());
        return tareas.get(opcion - 1);
    }

    private static Task seleccionarTareaDistinta(String mensaje, String taskIdExcluida) {
        List<Task> tareas = new ArrayList<>();
        for (Task tarea : projectService.getAllTasks()) {
            if (!tarea.getId().equals(taskIdExcluida)) {
                tareas.add(tarea);
            }
        }
        tareas.sort(Comparator.comparing(Task::getId));

        if (tareas.isEmpty()) {
            throw new IllegalStateException("No hay otra tarea disponible para elegir.");
        }

        System.out.println(mensaje);
        for (int i = 0; i < tareas.size(); i++) {
            Task tarea = tareas.get(i);
            System.out.println((i + 1) + ". " + tarea.getId() + " - " + tarea.getTitle());
        }

        int opcion = leerEnteroEnRango("Elegi una tarea: ", 1, tareas.size());
        return tareas.get(opcion - 1);
    }

    private static String generarTaskId() {
        int siguienteNumero = projectService.getAllTasks().size() + 1;
        String candidato = String.format("TASK-%03d", siguienteNumero);
        while (projectService.findTask(candidato) != null) {
            siguienteNumero++;
            candidato = String.format("TASK-%03d", siguienteNumero);
        }
        return candidato;
    }

    private static String generarSubtaskId() {
        int contador = 1;
        for (Task tarea : projectService.getAllTasks()) {
            contador += tarea.getSubtasks().size();
        }
        return String.format("SUB-%03d", contador);
    }
}
