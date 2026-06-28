/*
 Este archivo le corresponde a Serena.

 Objetos de model que vas a usar:
 - model.Task       -> para leer el estado de cada tarea y ver si esta bloqueada
 - model.TaskStatus -> para comparar el estado (ej: TaskStatus.BLOCKED)
   (importar con: import model.Task; import model.TaskStatus;)

 Ademas, vas a necesitar la estructura de Franco:
 - datastructures.DiccionarioTareas -> para buscar una tarea por su id
   (importar con: import datastructures.DiccionarioTareas;)
   Se recibe por constructor.

 Implementacion interna esperada:
 - HashMap<String, List<String>> listaAdyacencia

 Significado:
 - clave = taskId
 - valor = tareas que dependen de esa tarea

 Dependencia sugerida:
 - recibir DiccionarioTareas por constructor

 Metodos publicos esperados:
 - void agregarDependencia(String tareaOrigenId, String tareaDestinoId)
 - boolean estaBloqueada(String taskId)
 - List<String> obtenerBloqueantesDe(String taskId)
 - List<String> obtenerTareasDesbloqueadas()
 - boolean tieneCiclos()
 - void imprimirDependencias()

 Reglas importantes:
 - agregarDependencia debe llamar a tieneCiclos antes de dejar la arista
 - si hay ciclo, lanzar IllegalArgumentException
 - estaBloqueada debe usar el estado de las tareas (campo status de Task)
*/

package datastructures;

import model.Task;
import model.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrafoDirigido {

    private HashMap<String, List<String>> listaAdyacencia;
    private DiccionarioTareas diccionario;

    public GrafoDirigido(DiccionarioTareas diccionario) {
        this.listaAdyacencia = new HashMap<>();
        this.diccionario = diccionario;
    }

    public void agregarDependencia(String tareaOrigenId, String tareaDestinoId) {
        agregarVertice(tareaOrigenId);
        agregarVertice(tareaDestinoId);
        listaAdyacencia.get(tareaOrigenId).add(tareaDestinoId);
        if (tieneCiclos()) {
            listaAdyacencia.get(tareaOrigenId).remove(tareaDestinoId);
            throw new IllegalArgumentException("No se puede agregar la dependencia: genera un ciclo infinito en el proyecto.");
        }
    }

    public void agregarVertice(String taskId) {
        listaAdyacencia.putIfAbsent(taskId, new ArrayList<>());
    }

    public void agregarArista(String tareaOrigenId, String tareaDestinoId) {
        agregarDependencia(tareaOrigenId, tareaDestinoId);
    }

    public List<String> bfs(String origenId) {
        List<String> recorrido = new ArrayList<>();
        if (!listaAdyacencia.containsKey(origenId)) {
            return recorrido;
        }

        List<String> cola = new ArrayList<>();
        Map<String, Boolean> visitados = new HashMap<>();
        cola.add(origenId);
        visitados.put(origenId, true);

        int indiceActual = 0;
        while (indiceActual < cola.size()) {
            String actual = cola.get(indiceActual++);
            recorrido.add(actual);

            for (String vecino : listaAdyacencia.getOrDefault(actual, new ArrayList<>())) {
                if (!visitados.containsKey(vecino)) {
                    visitados.put(vecino, true);
                    cola.add(vecino);
                }
            }
        }

        return recorrido;
    }

    public List<String> dfs(String origenId) {
        List<String> recorrido = new ArrayList<>();
        if (!listaAdyacencia.containsKey(origenId)) {
            return recorrido;
        }

        Map<String, Boolean> visitados = new HashMap<>();
        dfsRecursivo(origenId, visitados, recorrido);
        return recorrido;
    }

    private void dfsRecursivo(String actual, Map<String, Boolean> visitados, List<String> recorrido) {
        visitados.put(actual, true);
        recorrido.add(actual);

        for (String vecino : listaAdyacencia.getOrDefault(actual, new ArrayList<>())) {
            if (!visitados.containsKey(vecino)) {
                dfsRecursivo(vecino, visitados, recorrido);
            }
        }
    }

    public void agregarDependenciaLegacy(String tareaOrigenId, String tareaDestinoId) {
        listaAdyacencia.putIfAbsent(tareaOrigenId, new ArrayList<>());
        listaAdyacencia.putIfAbsent(tareaDestinoId, new ArrayList<>());
        listaAdyacencia.get(tareaOrigenId).add(tareaDestinoId);
        if (tieneCiclos()) {
            listaAdyacencia.get(tareaOrigenId).remove(tareaDestinoId);
            throw new IllegalArgumentException("No se puede agregar la dependencia: genera un ciclo infinito en el proyecto.");
        }
    }

    public boolean estaBloqueada(String taskId) {
        List<String> bloqueantes = obtenerBloqueantesDe(taskId);

        for (String idBloqueante : bloqueantes) {
            Task tareaBloqueante = diccionario.obtener(idBloqueante);
            if (tareaBloqueante != null && tareaBloqueante.getStatus() != TaskStatus.DONE) {
                return true;
            }
        }
        return false;
    }

    public List<String> obtenerBloqueantesDe(String taskId) {
        List<String> bloqueantes = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : listaAdyacencia.entrySet()) {
            if (entry.getValue().contains(taskId)) {
                bloqueantes.add(entry.getKey());
            }
        }
        return bloqueantes;
    }

    public List<String> obtenerTareasDesbloqueadas() {
        List<String> desbloqueadas = new ArrayList<>();
        for (String taskId : listaAdyacencia.keySet()) {
            if (!estaBloqueada(taskId)) {
                desbloqueadas.add(taskId);
            }
        }
        return desbloqueadas;
    }

    public boolean tieneCiclos() {
        Map<String, Integer> estado = new HashMap<>();
        for (String nodo : listaAdyacencia.keySet()) {
            estado.put(nodo, 0);
        }

        for (String nodo : listaAdyacencia.keySet()) {
            if (estado.get(nodo) == 0) {
                if (dfsCiclo(nodo, estado)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfsCiclo(String nodo, Map<String, Integer> estado) {
        estado.put(nodo, 1);

        for (String vecino : listaAdyacencia.getOrDefault(nodo, new ArrayList<>())) {
            if (estado.get(vecino) == 1) {
                return true;
            }
            if (estado.get(vecino) == 0) {
                if (dfsCiclo(vecino, estado)) {
                    return true;
                }
            }
        }

        estado.put(nodo, 2);
        return false;
    }

    public void imprimirDependencias() {
        System.out.println("=== DEPENDENCIAS DEL PROYECTO ===");
        if (listaAdyacencia.isEmpty()) {
            System.out.println("No hay relaciones de bloqueo registradas.");
            return;
        }

        for (Map.Entry<String, List<String>> entry : listaAdyacencia.entrySet()) {
            String origen = entry.getKey();
            List<String> destinos = entry.getValue();

            if (!destinos.isEmpty()) {
                System.out.println("La tarea [" + origen + "] bloquea el inicio de: " + destinos);
            }
        }
        System.out.println("=================================");
    }
}
