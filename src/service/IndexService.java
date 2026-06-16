/*
 * Servicio compartido
 * 
 * Este servicio conecta principalmente:
 * - Franco: ArbolAVL y ArbolABB
 * - Gaspar: ArbolB
 * 
 * Estructuras que debe orquestar:
 * - ArbolAVL indiceBacklog
 * - ArbolABB indiceTags
 * - ArbolB indiceLogs
 * 
 * Metodos esperados:
 * - void indexTask(Task task)
 * - void removeTaskFromIndex(Task task)
 * - List<Object> getTasksByDateRange(LocalDate from, LocalDate to)
 * - List<String> getTasksByTag(String tag)
 * - void logAction(String action, String taskId, String userId)
 * - void printBacklog()
 * - void printTagIndex()
 * - void printAuditLog()
 */
