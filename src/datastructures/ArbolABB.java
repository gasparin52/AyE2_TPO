/*
 * Responsable: Franco
 * Tema: Arbol ABB
 *
 * Este arbol binario de busqueda (ABB) guarda tags como claves.
 * A cada tag le puede corresponder una o mas tareas (taskIds).
 *
 * Regla del ABB: todo lo que es menor va a la izquierda,
 *                todo lo que es mayor va a la derecha.
 */

package datastructures;

import java.util.ArrayList;
import java.util.List;

public class ArbolABB {

    // Clase interna: representa un nodo del arbol
    private class NodoABB {
        String clave;            // el tag (ej: "urgente", "backend")
        List<String> taskIds;   // lista de tareas que tienen ese tag
        NodoABB izquierdo;
        NodoABB derecho;

        NodoABB(String clave) {
            this.clave = clave;
            this.taskIds = new ArrayList<>();
            this.izquierdo = null;
            this.derecho = null;
        }
    }

    // La raiz del arbol (el primer nodo)
    private NodoABB raiz;

    public ArbolABB() {
        raiz = null;
    }

    // Inserta un tag con su taskId. Si el tag ya existe, agrega el taskId a la lista
    public void insertar(String tag, String taskId) {
        raiz = insertarRecursivo(raiz, tag, taskId);
    }

    private NodoABB insertarRecursivo(NodoABB nodo, String tag, String taskId) {
        // Si llegamos a un lugar vacio, creamos un nodo nuevo
        if (nodo == null) {
            NodoABB nuevo = new NodoABB(tag);
            nuevo.taskIds.add(taskId);
            return nuevo;
        }

        int comparacion = tag.compareTo(nodo.clave);

        if (comparacion < 0) {
            // El tag es menor, va a la izquierda
            nodo.izquierdo = insertarRecursivo(nodo.izquierdo, tag, taskId);
        } else if (comparacion > 0) {
            // El tag es mayor, va a la derecha
            nodo.derecho = insertarRecursivo(nodo.derecho, tag, taskId);
        } else {
            // El tag ya existe, solo agrego el taskId si no esta repetido
            if (!nodo.taskIds.contains(taskId)) {
                nodo.taskIds.add(taskId);
            }
        }

        return nodo;
    }

    // Busca un tag y devuelve la lista de taskIds. Si no existe, devuelve lista vacia
    public List<String> buscar(String tag) {
        NodoABB nodo = buscarRecursivo(raiz, tag);
        if (nodo == null) {
            return new ArrayList<>();
        }
        return nodo.taskIds;
    }

    private NodoABB buscarRecursivo(NodoABB nodo, String tag) {
        // Si llegamos a null, no esta en el arbol
        if (nodo == null) {
            return null;
        }

        int comparacion = tag.compareTo(nodo.clave);

        if (comparacion < 0) {
            return buscarRecursivo(nodo.izquierdo, tag);
        } else if (comparacion > 0) {
            return buscarRecursivo(nodo.derecho, tag);
        } else {
            // Lo encontramos
            return nodo;
        }
    }

    // Elimina un nodo del arbol por su tag
    public void eliminar(String tag) {
        raiz = eliminarRecursivo(raiz, tag);
    }

    private NodoABB eliminarRecursivo(NodoABB nodo, String tag) {
        if (nodo == null) {
            return null; // No estaba en el arbol
        }

        int comparacion = tag.compareTo(nodo.clave);

        if (comparacion < 0) {
            nodo.izquierdo = eliminarRecursivo(nodo.izquierdo, tag);
        } else if (comparacion > 0) {
            nodo.derecho = eliminarRecursivo(nodo.derecho, tag);
        } else {
            // Encontramos el nodo a eliminar

            // Caso 1: no tiene hijos (es una hoja)
            if (nodo.izquierdo == null && nodo.derecho == null) {
                return null;
            }

            // Caso 2: solo tiene hijo derecho
            if (nodo.izquierdo == null) {
                return nodo.derecho;
            }

            // Caso 3: solo tiene hijo izquierdo
            if (nodo.derecho == null) {
                return nodo.izquierdo;
            }

            // Caso 4: tiene dos hijos
            // Buscamos el minimo del subarbol derecho (el sucesor)
            NodoABB sucesor = encontrarMinimo(nodo.derecho);
            nodo.clave = sucesor.clave;
            nodo.taskIds = sucesor.taskIds;
            // Eliminamos el sucesor del subarbol derecho
            nodo.derecho = eliminarRecursivo(nodo.derecho, sucesor.clave);
        }

        return nodo;
    }

    // Devuelve el nodo con la clave mas chica de un subarbol
    private NodoABB encontrarMinimo(NodoABB nodo) {
        while (nodo.izquierdo != null) {
            nodo = nodo.izquierdo;
        }
        return nodo;
    }

    // Imprime todos los tags en orden alfabetico (izquierda -> raiz -> derecha)
    public void imprimirEnOrden() {
        System.out.println("=== Tags en orden ===");
        imprimirEnOrdenRecursivo(raiz);
    }

    private void imprimirEnOrdenRecursivo(NodoABB nodo) {
        if (nodo == null) {
            return;
        }
        imprimirEnOrdenRecursivo(nodo.izquierdo);
        System.out.println("Tag: " + nodo.clave + " -> Tareas: " + nodo.taskIds);
        imprimirEnOrdenRecursivo(nodo.derecho);
    }

    // Devuelve todos los tags del arbol en orden alfabetico
    public List<String> obtenerTodosLosTags() {
        List<String> lista = new ArrayList<>();
        obtenerTagsRecursivo(raiz, lista);
        return lista;
    }

    private void obtenerTagsRecursivo(NodoABB nodo, List<String> lista) {
        if (nodo == null) {
            return;
        }
        obtenerTagsRecursivo(nodo.izquierdo, lista);
        lista.add(nodo.clave);
        obtenerTagsRecursivo(nodo.derecho, lista);
    }
}
