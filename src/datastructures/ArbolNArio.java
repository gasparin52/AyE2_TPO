package datastructures;

import java.util.ArrayList;
import java.util.List;

import model.Subtask;

/**
 * Responsable: Gaspar
 * Tema: Arbol N-ario
 *
 * Arbol N-ario generico que representa la jerarquia del proyecto:
 *   Project (raiz)
 *     └── Module
 *          └── Task
 *               └── Subtask
 *
 * Cada nodo puede tener una cantidad arbitraria de hijos.
 * La busqueda se hace por DFS (Depth-First Search).
 */
public class ArbolNArio<T> {

    // Clase interna: representa un nodo del arbol
    // Se declara publica para poder devolver nodos desde metodos publicos
    public class NodoNArio<E> {
        E dato;
        List<NodoNArio<E>> hijos;

        NodoNArio(E dato) {
            this.dato = dato;
            this.hijos = new ArrayList<>();
        }

        public E getDato() {
            return dato;
        }

        public List<NodoNArio<E>> getHijos() {
            return hijos;
        }
    }

    // La raiz del arbol
    private NodoNArio<T> raiz;

    public ArbolNArio() {
        raiz = null;
    }

    // Devuelve la raiz del arbol
    public NodoNArio<T> getRaiz() {
        return raiz;
    }

    // Establece la raiz del arbol (se usa para el primer elemento, el Project)
    public void setRaiz(T dato) {
        this.raiz = new NodoNArio<>(dato);
    }

    // --- INSERTAR ---
    // Inserta un hijo debajo del nodo que contiene al padre.
    // Busca al padre por DFS y le agrega el hijo a su lista de hijos.
    public void insertar(T padre, T hijo) {
        if (raiz == null) {
            // Si el arbol esta vacio, el padre se convierte en raiz
            // y el hijo se agrega como su primer hijo
            raiz = new NodoNArio<>(padre);
            raiz.hijos.add(new NodoNArio<>(hijo));
            return;
        }

        NodoNArio<T> nodoPadre = buscarNodo(raiz, padre);
        if (nodoPadre != null) {
            nodoPadre.hijos.add(new NodoNArio<>(hijo));
        } else {
            System.out.println("Error: no se encontro el padre en el arbol.");
        }
    }

    // --- ELIMINAR ---
    // Elimina el nodo que contiene al objetivo y todo su subarbol.
    // No se puede eliminar la raiz con este metodo.
    public boolean eliminar(T objetivo) {
        if (raiz == null) {
            return false;
        }

        // Caso especial: si el objetivo es la raiz, la eliminamos
        if (raiz.dato.equals(objetivo)) {
            raiz = null;
            return true;
        }

        return eliminarRecursivo(raiz, objetivo);
    }

    private boolean eliminarRecursivo(NodoNArio<T> nodo, T objetivo) {
        // Recorremos los hijos del nodo actual
        for (int i = 0; i < nodo.hijos.size(); i++) {
            NodoNArio<T> hijo = nodo.hijos.get(i);

            if (hijo.dato.equals(objetivo)) {
                // Encontramos el nodo a eliminar: lo sacamos de la lista
                // (esto elimina todo el subarbol porque perdemos la referencia)
                nodo.hijos.remove(i);
                return true;
            }

            // Si no es este hijo, buscamos en sus descendientes (DFS)
            if (eliminarRecursivo(hijo, objetivo)) {
                return true;
            }
        }

        return false; // No se encontro en este subarbol
    }

    // --- BUSCAR ---
    // Busca un nodo por DFS y devuelve el NodoNArio que lo contiene.
    // Si no lo encuentra, devuelve null.
    public NodoNArio<T> buscar(T objetivo) {
        return buscarNodo(raiz, objetivo);
    }

    private NodoNArio<T> buscarNodo(NodoNArio<T> nodo, T objetivo) {
        if (nodo == null) {
            return null;
        }

        // Si el nodo actual contiene el dato buscado, lo devolvemos
        if (nodo.dato.equals(objetivo)) {
            return nodo;
        }

        // Sino, buscamos en cada hijo (DFS: primero en profundidad)
        for (NodoNArio<T> hijo : nodo.hijos) {
            NodoNArio<T> resultado = buscarNodo(hijo, objetivo);
            if (resultado != null) {
                return resultado;
            }
        }

        return null; // No se encontro en este subarbol
    }

    // --- IMPRIMIR ARBOL ---
    // Imprime el arbol con indentacion por nivel para visualizar la jerarquia.
    // Cada nivel se indenta con espacios adicionales.
    public void imprimirArbol() {
        if (raiz == null) {
            System.out.println("(arbol vacio)");
            return;
        }
        System.out.println("=== Jerarquia del proyecto ===");
        imprimirRecursivo(raiz, 0);
    }

    private void imprimirRecursivo(NodoNArio<T> nodo, int nivel) {
        // Creamos la indentacion segun el nivel
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < nivel; i++) {
            indent.append("   ");
        }

        // Si no es la raiz, agregamos el simbolo de rama
        if (nivel > 0) {
            indent.append("└── ");
        }

        System.out.println(indent.toString() + nodo.dato.toString());

        // Imprimimos cada hijo con un nivel mas de indentacion
        for (NodoNArio<T> hijo : nodo.hijos) {
            imprimirRecursivo(hijo, nivel + 1);
        }
    }

    // --- CALCULAR PROGRESO ---
    // Calcula el porcentaje de subtareas completadas dentro de un subarbol.
    // Solo cuenta las hojas que sean instancias de Subtask.
    // Si no hay subtareas, devuelve 0.
    public int calcularProgreso(NodoNArio<T> nodo) {
        if (nodo == null) {
            return 0;
        }

        int[] contadores = new int[2]; // [0] = total subtareas, [1] = completadas
        contarSubtareas(nodo, contadores);

        if (contadores[0] == 0) {
            return 0; // Sin subtareas, no hay progreso que calcular
        }

        // Calcula el porcentaje (entero)
        return (contadores[1] * 100) / contadores[0];
    }

    private void contarSubtareas(NodoNArio<T> nodo, int[] contadores) {
        // Si el dato del nodo es una Subtask, la contamos
        if (nodo.dato instanceof Subtask) {
            contadores[0]++; // total
            if (((Subtask) nodo.dato).isCompleted()) {
                contadores[1]++; // completadas
            }
        }

        // Recorremos todos los hijos (DFS)
        for (NodoNArio<T> hijo : nodo.hijos) {
            contarSubtareas(hijo, contadores);
        }
    }
}
