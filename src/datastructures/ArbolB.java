package datastructures;

import java.util.ArrayList;
import java.util.List;

import model.LogEntry;

/**
 * Responsable: Gaspar
 * Tema: Arbol B
 *
 * Arbol B de orden 3 (grado minimo t = 2) que almacena registros de auditoria (LogEntry).
 * La clave de cada entrada es el timestamp del LogEntry en formato String ISO.
 *
 * Propiedades del arbol B de grado minimo t = 2:
 * - Cada nodo puede tener como maximo 2*t - 1 = 3 claves
 * - Cada nodo puede tener como maximo 2*t = 4 hijos
 * - Cada nodo (excepto la raiz) tiene al menos t - 1 = 1 clave
 * - Todas las hojas estan al mismo nivel
 */
public class ArbolB {

    // Grado minimo del arbol B
    private static final int T = 2;

    // Clase interna: nodo del arbol B
    private class NodoArbolB {
        int t = T;
        List<String> claves;       // claves (timestamps ISO)
        List<LogEntry> valores;    // valores asociados a cada clave
        List<NodoArbolB> hijos;    // hijos del nodo
        boolean esHoja;            // true si es un nodo hoja

        NodoArbolB(boolean esHoja) {
            this.esHoja = esHoja;
            this.claves = new ArrayList<>();
            this.valores = new ArrayList<>();
            this.hijos = new ArrayList<>();
        }

        // Devuelve la cantidad de claves en este nodo
        int cantidadClaves() {
            return claves.size();
        }

        // Un nodo esta lleno cuando tiene 2*t - 1 claves
        boolean estaLleno() {
            return claves.size() == 2 * t - 1;
        }
    }

    // Raiz del arbol
    private NodoArbolB raiz;

    public ArbolB() {
        raiz = null;
    }

    // --- INSERTAR ---
    // Inserta un par clave-valor en el arbol B.
    // Si la raiz esta llena, la divide antes de insertar.
    public void insertar(String clave, LogEntry entrada) {
        if (raiz == null) {
            // Primera insercion: creamos la raiz como hoja
            raiz = new NodoArbolB(true);
            raiz.claves.add(clave);
            raiz.valores.add(entrada);
            return;
        }

        if (raiz.estaLleno()) {
            // La raiz esta llena: creamos una nueva raiz y dividimos la vieja
            NodoArbolB nuevaRaiz = new NodoArbolB(false);
            nuevaRaiz.hijos.add(raiz);
            dividirHijo(nuevaRaiz, 0);
            raiz = nuevaRaiz;
        }

        insertarNoLleno(raiz, clave, entrada);
    }

    // Divide el hijo en la posicion 'indice' del padre.
    // El hijo debe estar lleno (tiene 2*t - 1 claves).
    // Despues de dividir, el padre tiene una clave mas y un hijo mas.
    private void dividirHijo(NodoArbolB padre, int indice) {
        NodoArbolB hijoLleno = padre.hijos.get(indice);

        // Creamos un nuevo nodo que tendra las claves de la mitad derecha
        NodoArbolB nuevoNodo = new NodoArbolB(hijoLleno.esHoja);

        // La clave del medio sube al padre
        int indiceMedio = T - 1; // indice de la clave que sube (posicion t-1)

        // Copiamos las claves y valores de la mitad derecha al nuevo nodo
        // (desde t hasta 2*t - 2, que son las claves despues de la mediana)
        for (int j = T; j < 2 * T - 1; j++) {
            nuevoNodo.claves.add(hijoLleno.claves.get(j));
            nuevoNodo.valores.add(hijoLleno.valores.get(j));
        }

        // Si no es hoja, copiamos tambien los hijos de la mitad derecha
        if (!hijoLleno.esHoja) {
            for (int j = T; j < 2 * T; j++) {
                nuevoNodo.hijos.add(hijoLleno.hijos.get(j));
            }
        }

        // La clave y valor que suben al padre
        String claveSube = hijoLleno.claves.get(indiceMedio);
        LogEntry valorSube = hijoLleno.valores.get(indiceMedio);

        // Insertamos la clave que sube en la posicion correcta del padre
        padre.claves.add(indice, claveSube);
        padre.valores.add(indice, valorSube);

        // Insertamos el nuevo nodo como hijo del padre (a la derecha del hijo original)
        padre.hijos.add(indice + 1, nuevoNodo);

        // Limpiamos la mitad derecha y la mediana del hijo original
        // Hay que remover desde indiceMedio hasta el final
        int totalOriginal = hijoLleno.claves.size();
        for (int j = totalOriginal - 1; j >= indiceMedio; j--) {
            hijoLleno.claves.remove(j);
            hijoLleno.valores.remove(j);
        }

        // Remover hijos extra si no es hoja
        if (!hijoLleno.esHoja) {
            int totalHijos = hijoLleno.hijos.size();
            for (int j = totalHijos - 1; j >= T; j--) {
                hijoLleno.hijos.remove(j);
            }
        }
    }

    // Inserta una clave en un nodo que NO esta lleno.
    // Si el nodo es hoja, inserta directamente.
    // Si no es hoja, busca el hijo correcto y, si ese hijo esta lleno, lo divide primero.
    private void insertarNoLleno(NodoArbolB nodo, String clave, LogEntry entrada) {
        int i = nodo.cantidadClaves() - 1;

        if (nodo.esHoja) {
            // Buscamos la posicion correcta para insertar en orden
            // y desplazamos las claves mayores hacia la derecha
            // (como insertion sort)
            while (i >= 0 && clave.compareTo(nodo.claves.get(i)) < 0) {
                i--;
            }

            // Si la clave ya existe, actualizamos el valor
            if (i >= 0 && clave.equals(nodo.claves.get(i))) {
                nodo.valores.set(i, entrada);
                return;
            }

            // Insertamos en la posicion i+1
            nodo.claves.add(i + 1, clave);
            nodo.valores.add(i + 1, entrada);

        } else {
            // No es hoja: buscamos en que hijo corresponde bajar
            while (i >= 0 && clave.compareTo(nodo.claves.get(i)) < 0) {
                i--;
            }

            // Si la clave ya existe en un nodo interno, actualizamos
            if (i >= 0 && clave.equals(nodo.claves.get(i))) {
                nodo.valores.set(i, entrada);
                return;
            }

            i++; // el hijo correcto esta en la posicion i+1

            // Si el hijo esta lleno, lo dividimos antes de bajar
            if (nodo.hijos.get(i).estaLleno()) {
                dividirHijo(nodo, i);
                // Despues de dividir, la clave del medio subio al nodo actual.
                // Decidimos si la nueva clave va al hijo izquierdo o derecho
                if (clave.compareTo(nodo.claves.get(i)) > 0) {
                    i++;
                } else if (clave.equals(nodo.claves.get(i))) {
                    // La clave que subio es la misma que queremos insertar
                    nodo.valores.set(i, entrada);
                    return;
                }
            }

            insertarNoLleno(nodo.hijos.get(i), clave, entrada);
        }
    }

    // --- BUSCAR ---
    // Busca una clave en el arbol y devuelve el LogEntry asociado.
    // Si no la encuentra, devuelve null.
    public LogEntry buscar(String clave) {
        if (raiz == null) {
            return null;
        }
        return buscarRecursivo(raiz, clave);
    }

    private LogEntry buscarRecursivo(NodoArbolB nodo, String clave) {
        int i = 0;

        // Avanzamos mientras la clave sea mayor que la clave actual del nodo
        while (i < nodo.cantidadClaves() && clave.compareTo(nodo.claves.get(i)) > 0) {
            i++;
        }

        // Si encontramos la clave en este nodo, devolvemos el valor
        if (i < nodo.cantidadClaves() && clave.equals(nodo.claves.get(i))) {
            return nodo.valores.get(i);
        }

        // Si es hoja y no la encontramos, no existe
        if (nodo.esHoja) {
            return null;
        }

        // Si no es hoja, bajamos al hijo correspondiente
        return buscarRecursivo(nodo.hijos.get(i), clave);
    }

    // --- IMPRIMIR EN ORDEN ---
    // Imprime todos los registros del arbol en orden ascendente de clave (timestamp).
    // Recorre primero el hijo izquierdo, luego la clave, luego el hijo derecho, etc.
    public void imprimirEnOrden() {
        if (raiz == null) {
            System.out.println("(arbol B vacio - sin registros de auditoria)");
            return;
        }
        System.out.println("=== Registros de auditoria (ArbolB) ===");
        imprimirRecursivo(raiz);
    }

    private void imprimirRecursivo(NodoArbolB nodo) {
        // Para cada clave del nodo, imprimimos el subarbol izquierdo,
        // luego la clave, y seguimos con el proximo subarbol.
        for (int i = 0; i < nodo.cantidadClaves(); i++) {
            // Si no es hoja, imprimir el hijo antes de la clave i
            if (!nodo.esHoja) {
                imprimirRecursivo(nodo.hijos.get(i));
            }
            System.out.println(nodo.valores.get(i));
        }

        // Imprimir el ultimo hijo (el que esta despues de la ultima clave)
        if (!nodo.esHoja) {
            imprimirRecursivo(nodo.hijos.get(nodo.cantidadClaves()));
        }
    }
}
