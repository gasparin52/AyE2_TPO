/*
 Responsable: Gaspar
 Tema: Arbol N-ario

 Este archivo le corresponde a Gaspar.

 Objetos de model que vas a usar:
 - model.Project  -> es la raiz del arbol (nivel 1)
 - model.Module   -> es el segundo nivel (hijos del proyecto)
 - model.Task     -> es el tercer nivel (hijos de cada modulo)
 - model.Subtask  -> es el cuarto nivel (hijos de cada tarea)

 El arbol queda asi:
   Project (raiz)
    └── Module
         └── Task
              └── Subtask

 Clase esperada:
 - ArbolNArio<T>

 Clase interna esperada:
 - NodoNArio<T>

 Campos de NodoNArio:
 - T dato
 - List<NodoNArio<T>> hijos

 Metodos publicos esperados:
 - void insertar(T padre, T hijo)
 - boolean eliminar(T objetivo)
 - NodoNArio<T> buscar(T objetivo)
 - void imprimirArbol()
 - int calcularProgreso(NodoNArio<T> nodo)

 Reglas sugeridas:
 - busqueda por DFS
 - imprimir con indentacion por nivel
 - eliminar borra todo el subarbol
 - calcularProgreso devuelve porcentaje de subtareas completadas
   (usa el campo "completed" de Subtask)
*/
