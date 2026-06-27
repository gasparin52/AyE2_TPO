package datastructures;

import model.Notification;

/**
 * Responsable: Gaspar
 * Tema: Cola
 *
 * Cola FIFO (First In, First Out) implementada con nodos enlazados propios.
 * Almacena objetos Notification.
 *
 * El primero en entrar es el primero en salir.
 * Se mantienen referencias al frente y al final de la cola
 * para que encolar y desencolar sean O(1).
 *
 * Regla: no se usa LinkedList ni ninguna otra estructura de la biblioteca estandar.
 */
public class ColaNotificaciones {

    // Clase interna: nodo de la cola enlazada
    private class NodoCola {
        Notification dato;
        NodoCola siguiente;

        NodoCola(Notification dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }

    // Referencia al primer elemento de la cola (el proximo en salir)
    private NodoCola frente;

    // Referencia al ultimo elemento de la cola (el ultimo en entrar)
    private NodoCola fin;

    // Cantidad de elementos en la cola
    private int tamanio;

    public ColaNotificaciones() {
        this.frente = null;
        this.fin = null;
        this.tamanio = 0;
    }

    // --- ENCOLAR ---
    // Agrega una notificacion al final de la cola.
    public void encolar(Notification notification) {
        NodoCola nuevo = new NodoCola(notification);

        if (estaVacia()) {
            // Si la cola esta vacia, el nuevo nodo es tanto el frente como el fin
            frente = nuevo;
            fin = nuevo;
        } else {
            // Sino, lo agregamos al final y actualizamos la referencia
            fin.siguiente = nuevo;
            fin = nuevo;
        }

        tamanio++;
    }

    // --- DESENCOLAR ---
    // Saca y devuelve la notificacion del frente de la cola (la mas antigua).
    // Si la cola esta vacia, devuelve null.
    public Notification desencolar() {
        if (estaVacia()) {
            System.out.println("La cola de notificaciones esta vacia.");
            return null;
        }

        // Guardamos el dato del frente antes de sacarlo
        Notification dato = frente.dato;

        // Avanzamos el frente al siguiente nodo
        frente = frente.siguiente;

        // Si el frente quedo en null, la cola se vacio -> limpiamos el fin tambien
        if (frente == null) {
            fin = null;
        }

        tamanio--;
        return dato;
    }

    // --- VER FRENTE ---
    // Devuelve la notificacion del frente sin sacarla de la cola.
    // Si la cola esta vacia, devuelve null.
    public Notification verFrente() {
        if (estaVacia()) {
            return null;
        }
        return frente.dato;
    }

    // --- ESTA VACIA ---
    // Devuelve true si la cola no tiene elementos.
    public boolean estaVacia() {
        return tamanio == 0;
    }

    // --- TAMANIO ---
    // Devuelve la cantidad de notificaciones en la cola.
    public int tamanio() {
        return tamanio;
    }

    // --- IMPRIMIR TODAS ---
    // Muestra todas las notificaciones de la cola sin sacarlas.
    // Recorre desde el frente hasta el fin.
    public void imprimirTodas() {
        if (estaVacia()) {
            System.out.println("(cola de notificaciones vacia)");
            return;
        }

        System.out.println("=== Notificaciones pendientes (" + tamanio + ") ===");
        NodoCola actual = frente;
        int posicion = 1;
        while (actual != null) {
            System.out.println(posicion + ". " + actual.dato.toString());
            actual = actual.siguiente;
            posicion++;
        }
    }
}
