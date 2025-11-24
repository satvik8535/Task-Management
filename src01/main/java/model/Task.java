package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task {
    private int id;
    private String title;
    private String description;
    private TaskPriority priority;
    private LocalDate dueDate;
    private boolean completed;

    public Task(int id, String title, String description, TaskPriority priority, LocalDate dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.completed = false;
    }

    // Getters and setters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public TaskPriority getPriority() { return priority; }
    public void setPriority(TaskPriority priority) { this.priority = priority; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    @Override
    public String toString() {
        return String.format("ID: %d | Title: %s | Priority: %s | Due: %s | Completed: %s",
                id, title, priority, dueDate.format(DateTimeFormatter.ISO_LOCAL_DATE), completed ? "Yes" : "No");
    }

    public String toFileString() {
        return String.format("%d,%s,%s,%s,%s,%s",
                id, title, description, priority, dueDate, completed);
    }

    public static Task fromFileString(String line) {
        String[] parts = line.split(",");
        return new Task(
                Integer.parseInt(parts[0]),
                parts[1],
                parts[2],
                TaskPriority.valueOf(parts[3]),
                LocalDate.parse(parts[4]),
                Boolean.parseBoolean(parts[5])
        );
    }

    private Task(int id, String title, String description, TaskPriority priority, LocalDate dueDate, boolean completed) {
        this(id, title, description, priority, dueDate);
        this.completed = completed;
    }
}