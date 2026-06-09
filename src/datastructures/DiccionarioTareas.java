/*
 Responsable: Franco
 Tema: Conjunto / Diccionario

 Este archivo le corresponde a Franco.

 Implementacion esperada:
 - wrapper sobre HashMap<String, Task>
 - wrapper sobre HashSet<String>

 Campos:
 - HashMap<String, Task> mapaTareas
 - HashSet<String> miembrosProyecto

 Metodos publicos esperados:
 - void agregarTarea(Task tarea)
 - Task obtenerTarea(String taskId)
 - void eliminarTarea(String taskId)
 - boolean contieneTarea(String taskId)
 - void agregarMiembro(String userId)
 - boolean esMiembro(String userId)
 - void eliminarMiembro(String userId)
 - Set<String> obtenerTodosLosMiembros()
 - Collection<Task> obtenerTodasLasTareas()
 - void agregarTagATarea(String taskId, String tag)

 Regla importante:
 - agregarTagATarea debe evitar duplicados aunque Task.tags sea List
*/
