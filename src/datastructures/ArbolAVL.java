/*
 * Responsable: Franco
 * Tema: Arbol AVL
 *
 * El AVL es un arbol de busqueda que se mantiene balanceado automaticamente.
 * "Balanceado" significa que ningun lado del arbol puede ser mucho mas alto que el otro.
 *
 * Cuando se desbalancea, usa "rotaciones" para acomodarse.
 * Hay 4 tipos de rotaciones: derecha, izquierda, izquierda-derecha, derecha-izquierda.
 *
 * En este proyecto: se usa para el backlog ordenado por fecha o clave comparable.
 * La clave es Comparable (puede ser String, Integer, LocalDate, etc.)
 * El valor es Object (puede ser cualquier cosa).
 */

package datastructures;

import java.util.ArrayList;
import java.util.List;

public class ArbolAVL {

    // Clase interna: representa un nodo del arbol AVL
    private class NodoAVL {
        Comparable clave;   // la clave por la que se ordena (ej: una fecha)
        Object valor;       // el objeto guardado (ej: una Task)
        NodoAVL izquierdo;
        NodoAVL derecho;
        int altura;         // altura del nodo (cuantos niveles hay debajo)

        NodoAVL(Comparable clave, Object valor) {
            this.clave = clave;
            this.valor = valor;
            this.izquierdo = null;
            this.derecho = null;
            this.altura = 1; // un nodo nuevo tiene altura 1
        }
    }

    // La raiz del arbol
    private NodoAVL raiz;

    public ArbolAVL() {
        raiz = null;
    }

    // Devuelve la altura de un nodo. Si el nodo es null, devuelve 0
    private int altura(NodoAVL nodo) {
        if (nodo == null) return 0;
        return nodo.altura;
    }

    // El factor de balance es: altura(izquierda) - altura(derecha)
    // Si da mas de 1 o menos de -1, el arbol esta desbalanceado
    private int factorBalance(NodoAVL nodo) {
        if (nodo == null) return 0;
        return altura(nodo.izquierdo) - altura(nodo.derecho);
    }

    // Actualiza la altura de un nodo segun sus hijos
    private void actualizarAltura(NodoAVL nodo) {
        int altIzq = altura(nodo.izquierdo);
        int altDer = altura(nodo.derecho);
        nodo.altura = 1 + Math.max(altIzq, altDer);
    }

    // --- ROTACIONES ---

    // Rotacion a la derecha: cuando el lado izquierdo es demasiado alto
    //
    //      y                x
    //     / \              / \
    //    x   C    -->     A   y
    //   / \                  / \
    //  A   B                B   C
    //
    private NodoAVL rotarDerecha(NodoAVL y) {
        NodoAVL x = y.izquierdo;
        NodoAVL B = x.derecho;

        // Hacemos la rotacion
        x.derecho = y;
        y.izquierdo = B;

        // Actualizamos alturas (primero y, luego x)
        actualizarAltura(y);
        actualizarAltura(x);

        return x; // x es ahora la nueva raiz de este subarbol
    }

    // Rotacion a la izquierda: cuando el lado derecho es demasiado alto
    //
    //    x                  y
    //   / \                / \
    //  A   y      -->     x   C
    //     / \            / \
    //    B   C          A   B
    //
    private NodoAVL rotarIzquierda(NodoAVL x) {
        NodoAVL y = x.derecho;
        NodoAVL B = y.izquierdo;

        // Hacemos la rotacion
        y.izquierdo = x;
        x.derecho = B;

        // Actualizamos alturas (primero x, luego y)
        actualizarAltura(x);
        actualizarAltura(y);

        return y; // y es ahora la nueva raiz de este subarbol
    }

    // Rotacion izquierda-derecha: primero roto el hijo izquierdo a la izquierda,
    // despues el nodo actual a la derecha
    private NodoAVL rotarIzquierdaDerecha(NodoAVL nodo) {
        nodo.izquierdo = rotarIzquierda(nodo.izquierdo);
        return rotarDerecha(nodo);
    }

    // Rotacion derecha-izquierda: primero roto el hijo derecho a la derecha,
    // despues el nodo actual a la izquierda
    private NodoAVL rotarDerechaIzquierda(NodoAVL nodo) {
        nodo.derecho = rotarDerecha(nodo.derecho);
        return rotarIzquierda(nodo);
    }

    // Revisa si el nodo esta desbalanceado y aplica la rotacion que corresponde
    private NodoAVL rebalancear(NodoAVL nodo) {
        actualizarAltura(nodo);

        int balance = factorBalance(nodo);

        // Caso 1: lado izquierdo demasiado alto
        if (balance > 1) {
            if (factorBalance(nodo.izquierdo) >= 0) {
                // Rotacion simple a la derecha
                return rotarDerecha(nodo);
            } else {
                // Rotacion doble: izquierda-derecha
                return rotarIzquierdaDerecha(nodo);
            }
        }

        // Caso 2: lado derecho demasiado alto
        if (balance < -1) {
            if (factorBalance(nodo.derecho) <= 0) {
                // Rotacion simple a la izquierda
                return rotarIzquierda(nodo);
            } else {
                // Rotacion doble: derecha-izquierda
                return rotarDerechaIzquierda(nodo);
            }
        }

        // Si el balance esta bien (-1, 0 o 1), no hace falta rotar
        return nodo;
    }

    // Inserta un par clave-valor en el arbol
    public void insertar(Comparable clave, Object valor) {
        raiz = insertarRecursivo(raiz, clave, valor);
    }

    private NodoAVL insertarRecursivo(NodoAVL nodo, Comparable clave, Object valor) {
        // Lugar vacio: creamos el nodo nuevo
        if (nodo == null) {
            return new NodoAVL(clave, valor);
        }

        int comparacion = clave.compareTo(nodo.clave);

        if (comparacion < 0) {
            nodo.izquierdo = insertarRecursivo(nodo.izquierdo, clave, valor);
        } else if (comparacion > 0) {
            nodo.derecho = insertarRecursivo(nodo.derecho, clave, valor);
        } else {
            // La clave ya existe, actualizamos el valor
            nodo.valor = valor;
            return nodo;
        }

        // Despues de insertar, rebalanceamos si hace falta
        return rebalancear(nodo);
    }

    // Busca por clave y devuelve el valor. Si no existe, devuelve null
    public Object buscar(Comparable clave) {
        NodoAVL nodo = buscarRecursivo(raiz, clave);
        if (nodo == null) return null;
        return nodo.valor;
    }

    private NodoAVL buscarRecursivo(NodoAVL nodo, Comparable clave) {
        if (nodo == null) return null;

        int comparacion = clave.compareTo(nodo.clave);

        if (comparacion < 0) {
            return buscarRecursivo(nodo.izquierdo, clave);
        } else if (comparacion > 0) {
            return buscarRecursivo(nodo.derecho, clave);
        } else {
            return nodo; // lo encontramos
        }
    }

    // Elimina un nodo por clave
    public void eliminar(Comparable clave) {
        raiz = eliminarRecursivo(raiz, clave);
    }

    private NodoAVL eliminarRecursivo(NodoAVL nodo, Comparable clave) {
        if (nodo == null) return null;

        int comparacion = clave.compareTo(nodo.clave);

        if (comparacion < 0) {
            nodo.izquierdo = eliminarRecursivo(nodo.izquierdo, clave);
        } else if (comparacion > 0) {
            nodo.derecho = eliminarRecursivo(nodo.derecho, clave);
        } else {
            // Encontramos el nodo a eliminar

            // Caso 1: sin hijos
            if (nodo.izquierdo == null && nodo.derecho == null) {
                return null;
            }

            // Caso 2: solo hijo derecho
            if (nodo.izquierdo == null) {
                return nodo.derecho;
            }

            // Caso 3: solo hijo izquierdo
            if (nodo.derecho == null) {
                return nodo.izquierdo;
            }

            // Caso 4: dos hijos - buscamos el minimo del subarbol derecho
            NodoAVL sucesor = encontrarMinimo(nodo.derecho);
            nodo.clave = sucesor.clave;
            nodo.valor = sucesor.valor;
            nodo.derecho = eliminarRecursivo(nodo.derecho, sucesor.clave);
        }

        // Rebalanceamos despues de eliminar
        return rebalancear(nodo);
    }

    // Encuentra el nodo con la clave mas chica en un subarbol
    private NodoAVL encontrarMinimo(NodoAVL nodo) {
        while (nodo.izquierdo != null) {
            nodo = nodo.izquierdo;
        }
        return nodo;
    }

    // Devuelve todos los valores cuya clave esta entre "desde" y "hasta" (inclusive)
    public List<Object> buscarPorRango(Comparable desde, Comparable hasta) {
        List<Object> resultado = new ArrayList<>();
        buscarPorRangoRecursivo(raiz, desde, hasta, resultado);
        return resultado;
    }

    private void buscarPorRangoRecursivo(NodoAVL nodo, Comparable desde, Comparable hasta, List<Object> resultado) {
        if (nodo == null) return;

        // Si la clave actual es mayor que "desde", puede haber elementos validos a la izquierda
        if (nodo.clave.compareTo(desde) > 0) {
            buscarPorRangoRecursivo(nodo.izquierdo, desde, hasta, resultado);
        }

        // Si la clave actual esta dentro del rango, la agregamos
        if (nodo.clave.compareTo(desde) >= 0 && nodo.clave.compareTo(hasta) <= 0) {
            resultado.add(nodo.valor);
        }

        // Si la clave actual es menor que "hasta", puede haber elementos validos a la derecha
        if (nodo.clave.compareTo(hasta) < 0) {
            buscarPorRangoRecursivo(nodo.derecho, desde, hasta, resultado);
        }
    }

    // Imprime todos los elementos en orden (de menor a mayor clave)
    public void imprimirEnOrden() {
        System.out.println("=== AVL en orden ===");
        imprimirEnOrdenRecursivo(raiz);
    }

    public int mostrarAltura() {
        return altura(raiz);
    }

    public int factorEquilibrio() {
        return factorBalance(raiz);
    }

    private void imprimirEnOrdenRecursivo(NodoAVL nodo) {
        if (nodo == null) return;
        imprimirEnOrdenRecursivo(nodo.izquierdo);
        System.out.println("Clave: " + nodo.clave + " -> Valor: " + nodo.valor);
        imprimirEnOrdenRecursivo(nodo.derecho);
    }
}
