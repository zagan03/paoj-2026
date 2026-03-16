package com.pao.laboratory03.bonus.service;

import com.pao.laboratory03.bonus.exception.*;
import com.pao.laboratory03.bonus.model.*;
import java.util.*;

public class TaskService {
    private static TaskService instance;
    private final Map<String, Task> tasksById = new HashMap<>();
    private final Map<Priority, List<Task>> tasksByPriority = new EnumMap<>(Priority.class);
    private final List<String> auditLog = new ArrayList<>();

    private TaskService() {
        for (Priority p : Priority.values()) {
            tasksByPriority.put(p, new ArrayList<>());
        }
    }

    public static TaskService getInstance() {
        if (instance == null) {
            instance = new TaskService();
        }
        return instance;
    }

    public void addTask(String title, Priority priority, String assignee) {
        Task task = new Task(title, priority, assignee);
        tasksById.put(task.getId(), task);
        tasksByPriority.get(priority).add(task);
        // Formatare log conform cerintei
        auditLog.add("[ADD] " + task.getId() + ": '" + title + "' (" + priority + ")");
    }

    // 1. Metoda pentru asignare (punctul 2 din Main)
    public void assignTask(String taskId, String assignee) {
        Task task = tasksById.get(taskId);
        if (task == null) throw new TaskNotFoundException("Task negasit: " + taskId);

        task.setAssignee(assignee);
        auditLog.add("[ASSIGN] " + taskId + " -> " + assignee);
        System.out.println(taskId + " -> " + assignee);
    }

    public void changeStatus(String taskId, Status newStatus) {
        Task task = tasksById.get(taskId);
        if (task == null) {
            throw new TaskNotFoundException("Task-ul '" + taskId + "' nu a fost gasit");
        }

        Status oldStatus = task.getStatus();
        if (!oldStatus.canTransitionTo(newStatus)) {
            throw new InvalidTransitionException(oldStatus, newStatus);
        }

        task.setStatus(newStatus);
        auditLog.add("[STATUS] " + taskId + ": " + oldStatus + " -> " + newStatus);
        System.out.println(taskId + ": " + oldStatus + " -> " + newStatus + " \u2713");
    }

    // 2. Afisare pe prioritate (punctul 4 din Main)
    public void printTasksByPriority(Priority p) {
        List<Task> tasks = tasksByPriority.get(p);
        if (tasks.isEmpty()) {
            System.out.println("Nu sunt task-uri cu prioritate " + p);
        } else {
            tasks.forEach(System.out::println);
        }
    }

    // 3. Sumar statusuri (punctul 5 din Main)
    public void printStatusSummary() {
        Map<Status, Integer> counts = new EnumMap<>(Status.class);
        for (Status s : Status.values()) counts.put(s, 0);

        for (Task t : tasksById.values()) {
            counts.put(t.getStatus(), counts.get(t.getStatus()) + 1);
        }

        counts.forEach((status, count) -> System.out.println(status + ": " + count));
    }

    // 4. Task-uri neasignate (punctul 6 din Main)
    public void printUnassignedTasks() {
        tasksById.values().stream()
                .filter(t -> t.getAssignee() == null || t.getAssignee().isEmpty())
                .forEach(t -> System.out.println(t.getId() + ": " + t.getTitle()));
    }

    // 5. Calcul scor urgenta (punctul 7 din Main)
    public double calculateTotalUrgencyScore(int baseDays) {
        double total = 0;
        for (Task t : tasksById.values()) {
            total += t.getPriority().calculateScore(baseDays);
        }
        return total;
    }

    public void printAuditLog() {
        System.out.println("\n=== Audit Log ===");
        for (String entry : auditLog) {
            System.out.println(entry);
        }
    }

    public void printTasks() {
        for (Task t : tasksById.values()) {
            System.out.println("Adaugat: " + t);
        }
    }
}