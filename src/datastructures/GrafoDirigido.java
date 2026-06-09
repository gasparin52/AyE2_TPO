/*
 Responsable: Serena
 Tema: Grafo dirigido

 Este archivo le corresponde a Serena.

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
 - estaBloqueada debe usar el estado de las tareas
*/
