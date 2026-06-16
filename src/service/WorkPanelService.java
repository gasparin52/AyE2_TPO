/*
 * Servicio compartido
 * 
 * Este servicio conecta principalmente:
 * - Serena: MonticuloMaximo y PilaDeshacer
 * - Gaspar: ColaNotificaciones
 * 
 * Estructuras que debe orquestar:
 * - MonticuloMaximo panelTrabajo
 * - PilaDeshacer pilaDeshacer
 * - ColaNotificaciones colaNotificaciones
 * 
 * Metodos esperados:
 * - void addTaskToPanel(Task task)
 * - Task getNextTask()
 * - void modifyTask(Task task, ...)
 * - void undoLastAction(TaskDictionary dict)
 * - void sendNotification(String message, String userId)
 * - void processNextNotification()
 * - void showWorkPanel()
 * - void showPendingNotifications()
 * 
 * Reglas importantes:
 * - modifyTask debe apilar antes de modificar
 * - despues del cambio debe encolar notificacion
 */
