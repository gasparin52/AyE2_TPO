/*
 Responsable: Gaspar
 Tema: Cola

 Este archivo le corresponde a Gaspar.

 Objetos de model que vas a usar:
 - model.Notification -> es el dato que se guarda en cada nodo de la cola
   (importar con: import model.Notification;)

 Campos de Notification que te van a ser utiles:
 - String message        (el texto de la notificacion)
 - String targetUserId   (para quien es)
 - LocalDateTime timestamp (cuando se creo, se asigna automaticamente)

 Implementacion esperada:
 - cola FIFO con nodos propios (primero en entrar, primero en salir)

 Clase interna esperada:
 - NodoCola
   - Notification dato
   - NodoCola siguiente

 Metodos publicos esperados:
 - void encolar(Notification notification)
 - Notification desencolar()
 - Notification verFrente()
 - boolean estaVacia()
 - int tamanio()
 - void imprimirTodas()

 Regla importante:
 - no reemplazar con LinkedList de biblioteca
*/
