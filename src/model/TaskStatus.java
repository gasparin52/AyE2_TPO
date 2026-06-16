package model;

// Un enum es una lista fija de opciones.
// En lugar de usar numeros o texto suelto para el estado,
// usamos esto para evitar errores de tipeo.
public enum TaskStatus {
    PENDING,     // pendiente (todavia no se empezo)
    IN_PROGRESS, // en progreso (se esta trabajando)
    BLOCKED,     // bloqueada (hay algun impedimento)
    DONE         // completada
}
