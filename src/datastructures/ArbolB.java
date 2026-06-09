/*
 Responsable: Gaspar
 Tema: Arbol B

 Este archivo le corresponde a Gaspar.

 Implementacion esperada:
 - arbol B de orden 3
 - grado minimo t = 2

 Uso:
 - auditoria del sistema con LogEntry

 Clave:
 - String timestamp ISO

 Valor:
 - LogEntry

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
