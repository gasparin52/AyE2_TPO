package datastructures;

import java.util.ArrayList;
import java.util.List;

import model.Subtask;

public class ArbolNArio<T> {

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

    private NodoNArio<T> raiz;

    public ArbolNArio() {
        raiz = null;
    }

    public NodoNArio<T> getRaiz() {
        return raiz;
    }

    public void setRaiz(T dato) {
        this.raiz = new NodoNArio<>(dato);
    }

    public void insertar(T padre, T hijo) {
        if (raiz == null) {
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

    public boolean eliminar(T objetivo) {
        if (raiz == null) {
            return false;
        }

        if (raiz.dato.equals(objetivo)) {
            raiz = null;
            return true;
        }

        return eliminarRecursivo(raiz, objetivo);
    }

    private boolean eliminarRecursivo(NodoNArio<T> nodo, T objetivo) {
        for (int i = 0; i < nodo.hijos.size(); i++) {
            NodoNArio<T> hijo = nodo.hijos.get(i);

            if (hijo.dato.equals(objetivo)) {
                nodo.hijos.remove(i);
                return true;
            }

            if (eliminarRecursivo(hijo, objetivo)) {
                return true;
            }
        }

        return false;
    }

    public NodoNArio<T> buscar(T objetivo) {
        return buscarNodo(raiz, objetivo);
    }

    private NodoNArio<T> buscarNodo(NodoNArio<T> nodo, T objetivo) {
        if (nodo == null) {
            return null;
        }

        if (nodo.dato.equals(objetivo)) {
            return nodo;
        }

        for (NodoNArio<T> hijo : nodo.hijos) {
            NodoNArio<T> resultado = buscarNodo(hijo, objetivo);
            if (resultado != null) {
                return resultado;
            }
        }

        return null;
    }

    public void imprimirArbol() {
        if (raiz == null) {
            System.out.println("(arbol vacio)");
            return;
        }
        System.out.println("=== Jerarquia del proyecto ===");
        imprimirRecursivo(raiz, 0);
    }

    private void imprimirRecursivo(NodoNArio<T> nodo, int nivel) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < nivel; i++) {
            indent.append("   ");
        }

        if (nivel > 0) {
            indent.append("|-- ");
        }

        System.out.println(indent + nodo.dato.toString());

        for (NodoNArio<T> hijo : nodo.hijos) {
            imprimirRecursivo(hijo, nivel + 1);
        }
    }

    public int calcularProgreso(NodoNArio<T> nodo) {
        if (nodo == null) {
            return 0;
        }

        int[] contadores = new int[2];
        contarSubtareas(nodo, contadores);

        if (contadores[0] == 0) {
            return 0;
        }

        return (contadores[1] * 100) / contadores[0];
    }

    public List<T> recorridoProfundidad() {
        List<T> recorrido = new ArrayList<>();
        recorridoProfundidadRecursivo(raiz, recorrido);
        return recorrido;
    }

    private void recorridoProfundidadRecursivo(NodoNArio<T> nodo, List<T> recorrido) {
        if (nodo == null) {
            return;
        }

        recorrido.add(nodo.dato);
        for (NodoNArio<T> hijo : nodo.hijos) {
            recorridoProfundidadRecursivo(hijo, recorrido);
        }
    }

    public List<T> recorridoAmplitud() {
        List<T> recorrido = new ArrayList<>();
        if (raiz == null) {
            return recorrido;
        }

        List<NodoNArio<T>> cola = new ArrayList<>();
        int indiceActual = 0;
        cola.add(raiz);

        while (indiceActual < cola.size()) {
            NodoNArio<T> actual = cola.get(indiceActual++);
            recorrido.add(actual.dato);
            cola.addAll(actual.hijos);
        }

        return recorrido;
    }

    private void contarSubtareas(NodoNArio<T> nodo, int[] contadores) {
        if (nodo.dato instanceof Subtask) {
            contadores[0]++;
            if (((Subtask) nodo.dato).isCompleted()) {
                contadores[1]++;
            }
        }

        for (NodoNArio<T> hijo : nodo.hijos) {
            contarSubtareas(hijo, contadores);
        }
    }
}
