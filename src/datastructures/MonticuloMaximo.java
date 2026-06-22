/*
 Objetos de model que vas a usar:
 - model.Task -> la tarea que se guarda en el monticulo
   (importar con: import model.Task;)

 Campos de Task que te van a ser utiles:
 - String id
 - String title
 - int priority  -> este es el campo por el que se ordena el monticulo
 - TaskStatus status

 Implementacion esperada:
 - monticulo maximo sobre array dinamico
 - orden por Task.priority (prioridad mas alta = primero)

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

package datastructures;

import model.Task;
import java.util.ArrayList;

public class MonticuloMaximo {
    private ArrayList<NodoMonticulo> monticulo;

    private static class NodoMonticulo {
        Task tarea;
        int prioridad;

        NodoMonticulo(Task tarea) {
            this.tarea = tarea;
            this.prioridad = tarea.getPriority();
        }
    }

    public MonticuloMaximo() {
        this.monticulo = new ArrayList<>();
    }


    public void insertar(Task tarea) {
        if (tarea == null) return;
        NodoMonticulo nuevoNodo = new NodoMonticulo(tarea);
        monticulo.add(nuevoNodo);
        siftUp(monticulo.size() - 1);
    }

    public Task extraerMaximo() {
        if (estaVacio()) return null;

        Task maximo = monticulo.get(0).tarea;

        if (monticulo.size() == 1) {
            monticulo.remove(0);
        } else {
            monticulo.set(0, monticulo.remove(monticulo.size() - 1));
            siftDown(0);
        }

        return maximo;
    }

    public Task verMaximo() {
        if (estaVacio()) return null;
        return monticulo.get(0).tarea;
    }

    public void actualizarPrioridad(String taskId, int nuevaPrioridad) {
        for (int i = 0; i < monticulo.size(); i++) {
            NodoMonticulo nodo = monticulo.get(i);

            if (nodo.tarea.getId().equals(taskId)) {
                int prioridadAnterior = nodo.prioridad;
                nodo.prioridad = nuevaPrioridad;
                nodo.tarea.setPriority(nuevaPrioridad);

                if (nuevaPrioridad > prioridadAnterior) {
                    siftUp(i);
                } else if (nuevaPrioridad < prioridadAnterior) {
                    siftDown(i);
                }
                return;
            }
        }
        System.out.println("No se encontró la tarea con ID: " + taskId);
    }

    public boolean estaVacio() {
        return monticulo.isEmpty();
    }

    public int tamanio() {
        return monticulo.size();
    }

    public void imprimirPanel() {
        if (estaVacio()) {
            System.out.println("El panel está vacío.");
            return;
        }

        System.out.println("=== TRABAJO DIARIO (Por Urgencia) ===");

        MonticuloMaximo copia = new MonticuloMaximo();
        copia.monticulo = new ArrayList<>(this.monticulo);

        int posicion = 1;
        while (!copia.estaVacio()) {
            Task tareaActual = copia.extraerMaximo();
            System.out.println(posicion + ". [" + tareaActual.getId() + "] " +
                    tareaActual.getTitle() + " - Prioridad: " +
                    tareaActual.getPriority() + " (" + tareaActual.getStatus() + ")");
            posicion++;
        }
        System.out.println("==============================================");
    }

    private void siftUp(int indice) {
        while (indice > 0 && monticulo.get(indice).prioridad > monticulo.get(padre(indice)).prioridad) {
            intercambiar(indice, padre(indice));
            indice = padre(indice);
        }
    }

    private void siftDown(int indice) {
        int tamanio = monticulo.size();
        while (true) {
            int mayor = indice;
            int izq = hijoIzquierdo(indice);
            int der = hijoDerecho(indice);

            if (izq < tamanio && monticulo.get(izq).prioridad > monticulo.get(mayor).prioridad) {
                mayor = izq;
            }
            if (der < tamanio && monticulo.get(der).prioridad > monticulo.get(mayor).prioridad) {
                mayor = der;
            }

            if (mayor == indice) {
                break;
            }

            intercambiar(indice, mayor);
            indice = mayor;
        }
    }

    private void intercambiar(int i, int j) {
        NodoMonticulo temp = monticulo.get(i);
        monticulo.set(i, monticulo.get(j));
        monticulo.set(j, temp);
    }

    private int padre(int i) {
        return (i - 1) / 2;
    }

    private int hijoIzquierdo(int i) {
        return 2 * i + 1;
    }

    private int hijoDerecho(int i) {
        return 2 * i + 2;
    }
}