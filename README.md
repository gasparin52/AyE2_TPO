# TPO - Gestor de Proyectos Complejos

Proyecto base para un sistema de gestion de proyectos tipo Jira/Trello hecho en Java puro y orientado a consola.

## Estado actual

Este repositorio todavia no tiene implementacion de codigo.

Por ahora contiene:
- estructura de carpetas del proyecto
- archivos `.java` con instrucciones y responsabilidades
- reparto del trabajo entre integrantes

La idea es que cada integrante complete sus estructuras desde cero respetando la consigna del TPO.

## Estructura del proyecto

```text
src/
├── Main.java
├── REPARTO_EQUIPO.txt
├── model/
├── datastructures/
└── service/
```

## Reparto del equipo

### Franco
- Conjunto / Diccionario
- Arbol ABB
- Arbol AVL

Archivos:
- `src/datastructures/DiccionarioTareas.java`
- `src/datastructures/ArbolABB.java`
- `src/datastructures/ArbolAVL.java`

### Serena
- Pila
- Cola con prioridad
- Grafo

Archivos:
- `src/datastructures/PilaDeshacer.java`
- `src/datastructures/MonticuloMaximo.java`
- `src/datastructures/GrafoDirigido.java`

### Gaspar
- Arbol N-ario
- Arbol B
- Cola

Archivos:
- `src/datastructures/ArbolNArio.java`
- `src/datastructures/ArbolB.java`
- `src/datastructures/ColaNotificaciones.java`

## Archivos compartidos

Modelos:
- `src/model/Project.java`
- `src/model/Module.java`
- `src/model/Task.java`
- `src/model/Subtask.java`
- `src/model/User.java`
- `src/model/Notification.java`
- `src/model/LogEntry.java`
- `src/model/TaskStatus.java`

Servicios:
- `src/service/ProjectService.java`
- `src/service/WorkPanelService.java`
- `src/service/IndexService.java`

Entrada principal:
- `src/Main.java`

## Objetivo

Implementar todas las estructuras pedidas sin reemplazarlas por equivalentes de la biblioteca estandar cuando la consigna lo prohibe.

Si estan permitidos:
- `HashMap`
- `HashSet`
- `ArrayList`
- `List`
- `LocalDate`
- `LocalDateTime`

## Nota

Los archivos dentro de `src/` estan pensados como guia para el equipo.  
El archivo `src/REPARTO_EQUIPO.txt` resume quien hace cada parte.
