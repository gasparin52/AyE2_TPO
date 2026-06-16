/*
 Responsable: Gaspar
 Tema: Arbol B

 Este archivo le corresponde a Gaspar.

 Objetos de model que vas a usar:
 - model.LogEntry -> es el valor que se guarda en cada nodo
   (importar con: import model.LogEntry;)

 Campos de LogEntry que te van a ser utiles:
 - String id
 - String action   (que se hizo, ej: "crear tarea")
 - String taskId   (sobre que tarea)
 - String userId   (quien lo hizo)
 - LocalDateTime timestamp (cuando ocurrio)

 La clave del arbol es el timestamp de LogEntry en formato String (ISO).

 Implementacion esperada:
 - arbol B de orden 3
 - grado minimo t = 2

 Clase interna esperada:
 - NodoArbolB
   - int t = 2
   - List<String> claves
   - List<LogEntry> valores
   - List<NodoArbolB> hijos
   - boolean esHoja

 Metodos publicos esperados:
 - void insertar(String clave, LogEntry entrada)
 - LogEntry buscar(String clave)
 - void imprimirEnOrden()

 Metodos privados esperados:
 - void dividirHijo(NodoArbolB padre, int indice)
 - void insertarNoLleno(NodoArbolB nodo, String clave, LogEntry entrada)

 Regla importante:
 - priorizar insert y search
*/
