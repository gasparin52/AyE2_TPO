/*
 Responsable: Serena
 Tema: Pila

 Este archivo le corresponde a Serena.

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
