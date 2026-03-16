package com.pao.laboratory03.bonus.model;

public class Task {
    private static int counter = 1;
    private final String id;
    private String title;
    private Status status;
    private Priority priority;
    private String assignee;

    public Task(String title, Priority priority, String assignee) {
        this.id = String.format("T%03d", counter++);
        this.title = title;
        this.priority = priority;
        this.assignee = assignee;
        this.status = Status.TODO;
    }

    // Getters si Setters (status se schimba doar prin serviciu)
    public String getId() { return id; }
    public String getTitle() { return title; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public Priority getPriority() { return priority; }
    public String getAssignee() { return assignee; }

    @Override
    public String toString() {
        return String.format("[%s] %s | Status: %s | Prioritate: %s | Alocat: %s",
                id, title, status, priority, assignee);
    }
    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }
}