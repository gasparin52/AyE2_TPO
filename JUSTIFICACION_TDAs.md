# Justificación de TDAs y Análisis de Complejidad Temporal

## Gestor de Tareas — TPO Algoritmos y Estructuras de Datos II

---

## 1. Árbol N-Ario (`ArbolNArio<T>`)

### Uso en el sistema

Modela la **jerarquía del proyecto**: el nodo raíz es el `Project`, sus hijos son los `Module`, los hijos de cada módulo son las `Task`, y los hijos de cada tarea son las `Subtask`. Se usa en `ProjectService` para representar y recorrer toda la estructura del proyecto.

### ¿Por qué se eligió este TDA?

Un proyecto real tiene una estructura jerárquica donde cada nivel puede tener una cantidad variable de hijos: un proyecto tiene *N* módulos, cada módulo tiene *N* tareas, y cada tarea tiene *N* subtareas. El árbol N-ario es la estructura natural para este tipo de relación padre-hijos con grado variable, ya que no impone un límite fijo de hijos por nodo como lo haría un árbol binario.

Se descartó un árbol binario porque limitaría a 2 hijos por nodo, lo cual no refleja la realidad del dominio. También se descartó una lista plana porque perdería la información de anidamiento y pertenencia entre entidades.

### Análisis de complejidad temporal

> Donde `n` = cantidad total de nodos en el árbol.

| Operación | Complejidad | Justificación |
|---|---|---|
| `insertar(padre, hijo)` | **O(n)** | Busca el nodo padre recorriendo el árbol en profundidad antes de insertar. |
| `eliminar(objetivo)` | **O(n)** | Recorre recursivamente todos los nodos hasta encontrar el objetivo. |
| `buscar(objetivo)` | **O(n)** | Recorrido DFS completo en el peor caso. |
| `imprimirArbol()` | **O(n)** | Visita todos los nodos una vez. |
| `recorridoProfundidad()` | **O(n)** | Visita todos los nodos (DFS pre-orden). |
| `recorridoAmplitud()` | **O(n)** | Visita todos los nodos (BFS). |
| `calcularProgreso(nodo)` | **O(n)** | Recorre todo el subárbol contando subtareas completadas. |

---

## 2. Árbol B (`ArbolB`)

### Uso en el sistema

Almacena los **registros de auditoría** (`LogEntry`) del sistema. La clave de cada entrada es el timestamp en formato ISO (`String`). Se usa en `IndexService` para guardar y consultar el log cronológico de acciones realizadas sobre las tareas.

### ¿Por qué se eligió este TDA?

El Árbol B (de orden 3, grado mínimo t=2) garantiza que todas las hojas estén al mismo nivel y que el árbol se mantenga balanceado automáticamente, lo que asegura tiempos de búsqueda logarítmicos incluso con grandes volúmenes de registros de auditoría. A diferencia de un ABB clásico que puede degenerar en lista (O(n) en el peor caso), el Árbol B mantiene su balance estructuralmente.

Es especialmente adecuado para datos que crecen de forma monótona (como timestamps), donde un ABB sin balanceo degeneraría en una lista enlazada. El Árbol B evita este problema mediante su mecanismo de splits de nodos.

Se descartó un AVL porque, si bien también es balanceado, el Árbol B fue elegido como estructura diferenciada para demostrar el manejo de nodos con múltiples claves y la lógica de división de nodos, que es conceptualmente diferente a las rotaciones del AVL.

### Análisis de complejidad temporal

> Donde `n` = cantidad de registros de auditoría almacenados, y `t` = grado mínimo del árbol (t=2 en esta implementación).

| Operación | Complejidad | Justificación |
|---|---|---|
| `insertar(clave, entrada)` | **O(t · log_t(n))** | Desciende por el árbol (log_t(n) niveles) y en cada nivel puede dividir un nodo (costo O(t)). |
| `buscar(clave)` | **O(t · log_t(n))** | Desciende nivel por nivel, comparando hasta t claves por nodo. |
| `imprimirEnOrden()` | **O(n)** | Recorre todos los nodos visitando cada clave exactamente una vez. |

> **Nota:** con t=2, log_t(n) = log₂(n), por lo que en la práctica insertar y buscar son **O(log n)**.

---

## 3. Cola de Notificaciones (`ColaNotificaciones`)

### Uso en el sistema

Gestiona las **notificaciones pendientes** dirigidas a los usuarios. Cada vez que se modifica o se deshace un cambio sobre una tarea, se encola una `Notification`. El usuario puede procesarlas una a una en orden FIFO. Se usa en `WorkPanelService`.

### ¿Por qué se eligió este TDA?

Las notificaciones deben procesarse en el mismo orden en que se generaron (la primera en llegar es la primera en ser atendida), lo cual es exactamente la semántica FIFO (First In, First Out) que provee una cola. Implementarla con nodos enlazados propios (sin usar `LinkedList` de Java) permite que tanto encolar como desencolar sean O(1) al mantener referencias al frente y al fin.

Se descartó una pila porque procesaría las notificaciones en orden inverso al de llegada (LIFO), lo cual no tiene sentido para un sistema de notificaciones. Se descartó una lista con acceso por índice porque desencolar del frente sería O(n) al tener que desplazar elementos.

### Análisis de complejidad temporal

> Donde `n` = cantidad de notificaciones en la cola.

| Operación | Complejidad | Justificación |
|---|---|---|
| `encolar(notification)` | **O(1)** | Se agrega al final usando la referencia `fin`. |
| `desencolar()` | **O(1)** | Se extrae del frente usando la referencia `frente`. |
| `verFrente()` | **O(1)** | Acceso directo al nodo `frente`. |
| `estaVacia()` | **O(1)** | Comparación del campo `tamanio` con 0. |
| `tamanio()` | **O(1)** | Retorno directo del campo `tamanio`. |
| `imprimirTodas()` | **O(n)** | Recorre toda la lista enlazada desde el frente. |

---

## 4. Diccionario de Tareas (`DiccionarioTareas`)

### Uso en el sistema

Es el **registro central de tareas** del proyecto. Permite agregar, buscar, eliminar y listar tareas por su ID en tiempo constante. También mantiene un conjunto de miembros del proyecto. Es utilizado por `ProjectService`, `WorkPanelService` (para deshacer cambios) y `GrafoDirigido` (para verificar estados de bloqueo).

### ¿Por qué se eligió este TDA?

El diccionario (basado en `HashMap`) es la estructura ideal para un registro de entidades indexadas por un identificador único (`taskId`). El acceso, inserción y eliminación por clave son O(1) en promedio, lo que permite que las consultas frecuentes de tareas por ID sean instantáneas. El `HashSet` para miembros permite verificar pertenencia en O(1).

Se descartó una lista porque buscar una tarea por ID requeriría O(n). Se descartó un árbol porque, si bien ofrece orden, el sistema no necesita iterar las tareas en un orden particular por ID, sino acceder directamente a ellas.

### Análisis de complejidad temporal

> Donde `n` = cantidad de tareas almacenadas, `m` = cantidad de miembros.

| Operación | Complejidad | Justificación |
|---|---|---|
| `agregarTarea(tarea)` | **O(1)** amortizado | `HashMap.put()` es O(1) amortizado. |
| `obtenerTarea(taskId)` | **O(1)** amortizado | `HashMap.get()` es O(1) amortizado. |
| `eliminarTarea(taskId)` | **O(1)** amortizado | `HashMap.remove()` es O(1) amortizado. |
| `contieneTarea(taskId)` | **O(1)** amortizado | `HashMap.containsKey()` es O(1) amortizado. |
| `obtenerTodasLasTareas()` | **O(1)** | Retorna la vista `values()` del mapa (no copia). |
| `agregarMiembro(userId)` | **O(1)** amortizado | `HashSet.add()` es O(1) amortizado. |
| `esMiembro(userId)` | **O(1)** amortizado | `HashSet.contains()` es O(1) amortizado. |
| `agregarTagATarea(taskId, tag)` | **O(k)** | Busca la tarea en O(1), luego verifica duplicados en la lista de tags (O(k), con k = cantidad de tags). |

---

## 5. Grafo Dirigido (`GrafoDirigido`)

### Uso en el sistema

Modela las **dependencias entre tareas**: si la tarea A bloquea a la tarea B, existe una arista dirigida A → B. Se usa en `ProjectService` para agregar dependencias, detectar tareas bloqueadas, y prevenir ciclos (dependencias circulares).

### ¿Por qué se eligió este TDA?

Las dependencias entre tareas son inherentemente una relación dirigida: "A bloquea a B" no implica que "B bloquea a A". Un grafo dirigido con lista de adyacencia es la representación natural de este tipo de relaciones. La lista de adyacencia (vs. matriz de adyacencia) es más eficiente en espacio cuando hay pocas dependencias en relación al número de tareas (grafo disperso), que es el caso típico en un gestor de proyectos.

Se descartó un árbol porque las dependencias pueden ser muchos-a-muchos (una tarea puede bloquear varias, y puede ser bloqueada por varias). Se descartó una matriz de adyacencia porque el grafo es disperso y la matriz desperdiciaría O(V²) de espacio.

### Análisis de complejidad temporal

> Donde `V` = cantidad de vértices (tareas en el grafo), `E` = cantidad de aristas (dependencias).

| Operación | Complejidad | Justificación |
|---|---|---|
| `agregarVertice(taskId)` | **O(1)** amortizado | `putIfAbsent()` sobre un `HashMap`. |
| `agregarDependencia(from, to)` | **O(V + E)** | Agrega la arista en O(1), pero luego ejecuta `tieneCiclos()` que recorre todo el grafo. |
| `estaBloqueada(taskId)` | **O(V + E)** | Llama a `obtenerBloqueantesDe()` que itera por toda la lista de adyacencia. |
| `obtenerBloqueantesDe(taskId)` | **O(V + E)** | Recorre todas las entradas del mapa y sus listas para encontrar aristas entrantes al nodo. |
| `obtenerTareasDesbloqueadas()` | **O(V · (V + E))** | Para cada vértice llama a `estaBloqueada()`. |
| `tieneCiclos()` | **O(V + E)** | DFS con coloreo de 3 estados (blanco/gris/negro) sobre todo el grafo. |
| `bfs(origenId)` | **O(V + E)** | Recorrido BFS estándar. |
| `dfs(origenId)` | **O(V + E)** | Recorrido DFS estándar. |
| `imprimirDependencias()` | **O(V + E)** | Itera todas las entradas del mapa y sus listas. |

---

## 6. Montículo Máximo (`MonticuloMaximo`)

### Uso en el sistema

Implementa el **panel de trabajo diario**: las tareas se organizan automáticamente por prioridad, de manera que la de mayor prioridad siempre esté disponible primero. Se usa en `WorkPanelService` para insertar tareas, extraer la más prioritaria y actualizar prioridades.

### ¿Por qué se eligió este TDA?

El panel de trabajo necesita extraer siempre la tarea más prioritaria de forma eficiente. Un montículo máximo (max-heap) sobre un array dinámico permite insertar y extraer el máximo en O(log n), lo que es significativamente más eficiente que ordenar una lista completa cada vez (O(n log n)) o buscar el máximo en una lista desordenada (O(n)).

Se descartó una lista ordenada porque, si bien permite extraer el máximo en O(1), la inserción requeriría O(n) para mantener el orden. Se descartó un ABB porque en el peor caso (datos ya ordenados) degenera en O(n). El montículo ofrece el mejor balance entre inserción y extracción.

### Análisis de complejidad temporal

> Donde `n` = cantidad de tareas en el montículo.

| Operación | Complejidad | Justificación |
|---|---|---|
| `insertar(tarea)` | **O(log n)** | Se agrega al final del array y sube con `siftUp`. La altura del heap es log₂(n). |
| `extraerMaximo()` | **O(log n)** | Se reemplaza la raíz con el último elemento y baja con `siftDown`. |
| `verMaximo()` | **O(1)** | Acceso directo al índice 0 del array. |
| `actualizarPrioridad(taskId, nueva)` | **O(n)** | Búsqueda lineal del taskId + O(log n) para `siftUp`/`siftDown`. Dominado por la búsqueda. |
| `estaVacio()` | **O(1)** | Verifica si el `ArrayList` está vacío. |
| `tamanio()` | **O(1)** | Retorna el tamaño del `ArrayList`. |
| `imprimirPanel()` | **O(n log n)** | Copia el montículo y extrae todos los elementos en orden (n extracciones de O(log n)). |

---

## 7. Pila Deshacer (`PilaDeshacer`)

### Uso en el sistema

Implementa la funcionalidad de **deshacer** (undo): antes de cada modificación a una tarea, se apila una instantánea (`InstantaneaTarea`) con el estado previo completo. Al deshacer, se desapila la última instantánea y se restaura la tarea a ese estado. Se usa en `WorkPanelService`.

### ¿Por qué se eligió este TDA?

El comportamiento de "deshacer" es intrínsecamente LIFO (Last In, First Out): el último cambio realizado es el primero que se debe revertir. Una pila implementada con lista enlazada simple ofrece apilar y desapilar en O(1), sin desperdicio de espacio ni necesidad de redimensionar arrays.

Se descartó una cola porque deshacer en orden FIFO revertiría el cambio más antiguo primero, lo cual no es la semántica esperada de un undo. Se descartó un array dinámico porque la lista enlazada permite O(1) sin amortización ni copias de arreglo.

### Análisis de complejidad temporal

> Donde `n` = cantidad de instantáneas almacenadas en la pila.

| Operación | Complejidad | Justificación |
|---|---|---|
| `apilar(tarea)` | **O(k)** | Crea una `InstantaneaTarea` copiando los campos de la tarea (la copia de la lista de tags es O(k), con k = cantidad de tags). El push en sí es O(1). |
| `desapilar()` | **O(1)** | Se extrae el tope y se actualiza la referencia. |
| `verTope()` | **O(1)** | Acceso directo al nodo `tope`. |
| `estaVacia()` | **O(1)** | Verifica si `tope == null`. |
| `tamanio()` | **O(1)** | Retorno directo del campo `tamanio`. |
| `imprimirHistorial()` | **O(n)** | Recorre todos los nodos de la lista enlazada. |

---

## 8. Árbol ABB (`ArbolABB`)

### Uso en el sistema

Funciona como un **índice de tags**: cada nodo almacena un tag (String) como clave y una lista de IDs de tareas asociadas a ese tag. Se usa en `IndexService` para buscar tareas por etiqueta y para listar todos los tags en orden alfabético.

### ¿Por qué se eligió este TDA?

El ABB permite almacenar los tags de forma ordenada e insertar y buscar en O(log n) en el caso promedio. El recorrido in-orden produce la lista de tags en orden alfabético naturalmente, lo cual es útil para mostrar el índice de tags. Cada nodo puede almacenar múltiples taskIds, lo que evita duplicar nodos para tags repetidos.

Se descartó un HashMap porque, si bien la búsqueda es O(1), no permite recorrer los tags en orden alfabético sin ordenar aparte. Se descartó un AVL porque, para el volumen esperado de tags (decenas, no miles), el ABB simple es suficiente y más sencillo de implementar, y la probabilidad de degeneración es baja con claves alfanuméricas dispersas.

### Análisis de complejidad temporal

> Donde `n` = cantidad de tags distintos en el árbol, `h` = altura del árbol.

| Operación | Complejidad | Justificación |
|---|---|---|
| `insertar(tag, taskId)` | **O(h)** — promedio O(log n), peor caso O(n) | Desciende por el árbol comparando la clave. Si el ABB se desbalancea (claves en orden), la altura puede ser n. |
| `buscar(tag)` | **O(h)** — promedio O(log n), peor caso O(n) | Misma lógica que insertar. |
| `eliminar(tag)` | **O(h)** — promedio O(log n), peor caso O(n) | Busca el nodo y maneja los 4 casos de eliminación (0, 1 o 2 hijos + sucesor). |
| `imprimirEnOrden()` | **O(n)** | Recorrido in-orden completo del árbol. |
| `obtenerTodosLosTags()` | **O(n)** | Recorrido in-orden acumulando claves en una lista. |

---

## 9. Árbol AVL (`ArbolAVL`)

### Uso en el sistema

Implementa el **backlog ordenado por fecha**: cada nodo tiene como clave una `LocalDate` de creación y como valor la lista de tareas creadas en esa fecha. Se usa en `IndexService` para consultar tareas por rango de fechas y para mostrar el backlog cronológicamente.

### ¿Por qué se eligió este TDA?

El AVL garantiza **balance automático** mediante rotaciones, lo que asegura O(log n) en inserción, búsqueda y eliminación en **todos** los casos (no solo en promedio). Esto es crítico para el backlog porque las fechas de creación tienden a ser secuenciales (crecientes), lo que haría que un ABB simple degenere en una lista enlazada con complejidad O(n).

Además, la operación `buscarPorRango(desde, hasta)` se beneficia de la estructura balanceada: puede podar ramas enteras que quedan fuera del rango, reduciendo significativamente la cantidad de nodos visitados.

Se descartó un ABB simple porque con fechas secuenciales degeneraría en O(n). Se descartó un HashMap porque no soporta consultas por rango de forma eficiente.

### Análisis de complejidad temporal

> Donde `n` = cantidad de fechas distintas en el árbol.

| Operación | Complejidad | Justificación |
|---|---|---|
| `insertar(clave, valor)` | **O(log n)** | Inserción BST + rebalanceo con a lo sumo O(1) rotaciones, recorriendo O(log n) niveles. |
| `buscar(clave)` | **O(log n)** | Descenso por el árbol balanceado. Garantizado por la propiedad AVL (|FB| ≤ 1). |
| `eliminar(clave)` | **O(log n)** | Eliminación BST + rebalanceo en O(log n) niveles. |
| `buscarPorRango(desde, hasta)` | **O(log n + k)** | Donde k = cantidad de resultados. Poda ramas fuera del rango, visita O(log n) nodos para llegar al rango + los k nodos del resultado. |
| `imprimirEnOrden()` | **O(n)** | Recorrido in-orden completo. |
| Rotaciones (`rotarDerecha`, etc.) | **O(1)** | Cada rotación reasigna un número fijo de punteros. |

---

## Resumen comparativo

| TDA | Estructura interna | Operación clave | Complejidad | Uso principal |
|---|---|---|---|---|
| **Árbol N-Ario** | Nodos con lista de hijos | Insertar / Buscar | O(n) | Jerarquía del proyecto |
| **Árbol B** | Nodos multi-clave balanceados | Insertar / Buscar | O(log n) | Log de auditoría |
| **Cola** | Lista enlazada (frente/fin) | Encolar / Desencolar | O(1) | Notificaciones |
| **Diccionario** | HashMap + HashSet | Obtener por ID | O(1) amortizado | Registro central de tareas |
| **Grafo Dirigido** | Lista de adyacencia (HashMap) | Agregar dependencia | O(V + E) | Dependencias entre tareas |
| **Montículo Máximo** | Array dinámico (heap) | Insertar / Extraer máx. | O(log n) | Panel de trabajo por prioridad |
| **Pila** | Lista enlazada simple | Apilar / Desapilar | O(1) | Deshacer cambios (undo) |
| **Árbol ABB** | Nodos binarios de búsqueda | Insertar / Buscar | O(log n) promedio | Índice de tags |
| **Árbol AVL** | BST auto-balanceado | Insertar / Buscar / Rango | O(log n) garantizado | Backlog por fecha |
