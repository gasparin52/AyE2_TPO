/*
 Responsable: Serena
 Tema: Cola con prioridad

 Este archivo le corresponde a Serena.

 Implementacion esperada:
 - monticulo maximo sobre array dinamico
 - orden por Task.priority

 Clase interna o separada esperada:
 - NodoMonticulo
   - Task tarea
   - int prioridad

 Metodos publicos esperados:
 - void insertar(Task tarea)
 - Task extraerMaximo()
 - Task verMaximo()
 - void actualizarPrioridad(String taskId, int nuevaPrioridad)
 - boolean estaVacio()
 - int tamanio()
 - void imprimirPanel()

 Metodos privados esperados:
 - void siftUp(int indice)
 - void siftDown(int indice)
 - void intercambiar(int i, int j)
 - int padre(int i)
 - int hijoIzquierdo(int i)
 - int hijoDerecho(int i)

 Regla importante:
 - imprimirPanel debe mostrar prioridades sin destruir el monticulo original
*/
