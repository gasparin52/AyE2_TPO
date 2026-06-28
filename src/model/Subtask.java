package model;

public class Subtask {

    private String id;
    private String title;
    private String taskId;
    private boolean completed;
    private int priority;

    public Subtask(String id, String title, String taskId, int priority) {
        this.id = id;
        this.title = title;
        this.taskId = taskId;
        this.priority = priority;
        this.completed = false;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTaskId() {
        return taskId;
    }

    public boolean isCompleted() {
        return completed;
    }

    public int getPriority() {
        return priority;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        String estado = completed ? "completada" : "pendiente";
        return "Subtarea[" + id + "] " + title + " - " + estado;
    }
}
