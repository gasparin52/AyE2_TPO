/*
 * Servicio compartido
 * 
 * Este servicio conecta principalmente:
 * - Gaspar: ArbolNArio
 * - Serena: GrafoDirigido
 * - Franco: DiccionarioTareas
 * 
 * Estructuras que debe orquestar:
 * - ArbolNArio<Object> arbolProyecto
 * - GrafoDirigido grafoDependencias
 * - DiccionarioTareas diccionarioTareas
 * 
 * Metodos esperados:
 * - void createProject(String id, String name, String description)
 * - void addModule(String projectId, String moduleId, String moduleName)
 * - void addTask(String moduleId, Task task)
 * - void addSubtask(String taskId, Subtask subtask)
 * - void addDependency(String fromId, String toId)
 * - void assignMember(String userId)
 * - Task findTask(String taskId)
 * - void printProjectHierarchy()
 * - void printBlockedTasks()
 * 
 * Reglas importantes:
 * - addTask inserta en arbol y diccionario
 * - findTask usa el diccionario y no el arbol
 */
