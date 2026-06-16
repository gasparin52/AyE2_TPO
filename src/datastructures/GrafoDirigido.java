/*
 Responsable: Serena
 Tema: Grafo dirigido

 Este archivo le corresponde a Serena.

 Objetos de model que vas a usar:
 - model.Task       -> para leer el estado de cada tarea y ver si esta bloqueada
 - model.TaskStatus -> para comparar el estado (ej: TaskStatus.BLOCKED)
   (importar con: import model.Task; import model.TaskStatus;)

 Ademas, vas a necesitar la estructura de Franco:
 - datastructures.DiccionarioTareas -> para buscar una tarea por su id
   (importar con: import datastructures.DiccionarioTareas;)
   Se recibe por constructor.

 Implementacion interna esperada:
 - HashMap<String, List<String>> listaAdyacencia

 Significado:
 - clave = taskId
 - valor = tareas que dependen de esa tarea

 Dependencia sugerida:
 - recibir DiccionarioTareas por constructor

 Metodos publicos esperados:
 - void agregarDependencia(String tareaOrigenId, String tareaDestinoId)
 - boolean estaBloqueada(String taskId)
 - List<String> obtenerBloqueantesDe(String taskId)
 - List<String> obtenerTareasDesbloqueadas()
 - boolean tieneCiclos()
 - void imprimirDependencias()

 Reglas importantes:
 - agregarDependencia debe llamar a tieneCiclos antes de dejar la arista
 - si hay ciclo, lanzar IllegalArgumentException
 - estaBloqueada debe usar el estado de las tareas (campo status de Task)
*/
