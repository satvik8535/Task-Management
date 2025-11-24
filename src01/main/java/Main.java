import model.Task;
import model.TaskPriority;
import service.TaskService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static TaskService taskService = new TaskService();
    private static Scanner scanner = new Scanner(System.in);
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

    public static void main(String[] args) {
        System.out.println("=== Personal Task Manager ===");
        showMainMenu();
    }

    private static void showMainMenu() {
        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Add New Task");
            System.out.println("2. View All Tasks");
            System.out.println("3. View Tasks by Priority");
            System.out.println("4. View Pending Tasks");
            System.out.println("5. Mark Task as Completed");
            System.out.println("6. Delete Task");
            System.out.println("7. Show Statistics");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1 -> addNewTask();
                    case 2 -> viewAllTasks();
                    case 3 -> viewTasksByPriority();
                    case 4 -> viewPendingTasks();
                    case 5 -> markTaskCompleted();
                    case 6 -> deleteTask();
                    case 7 -> showStatistics();
                    case 8 -> {
                        System.out.println("Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static void addNewTask() {
        System.out.println("\n--- Add New Task ---");
        
        System.out.print("Enter task title: ");
        String title = scanner.nextLine();
        
        System.out.print("Enter task description (optional): ");
        String description = scanner.nextLine();
        if (description.isEmpty()) description = "No description";
        
        TaskPriority priority = selectPriority();
        LocalDate dueDate = selectDueDate();
        
        if (taskService.addTask(title, description, priority, dueDate)) {
            System.out.println("Task added successfully!");
        } else {
            System.out.println("Failed to add task. Please check your input.");
        }
    }

    private static TaskPriority selectPriority() {
        while (true) {
            System.out.println("Select priority:");
            System.out.println("1. LOW");
            System.out.println("2. MEDIUM");
            System.out.println("3. HIGH");
            System.out.println("4. URGENT");
            System.out.print("Choose (1-4): ");
            
            String input = scanner.nextLine();
            switch (input) {
                case "1": return TaskPriority.LOW;
                case "2": return TaskPriority.MEDIUM;
                case "3": return TaskPriority.HIGH;
                case "4": return TaskPriority.URGENT;
                default: System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static LocalDate selectDueDate() {
        while (true) {
            System.out.print("Enter due date (YYYY-MM-DD): ");
            String dateInput = scanner.nextLine();
            
            try {
                LocalDate dueDate = LocalDate.parse(dateInput, dateFormatter);
                if (dueDate.isBefore(LocalDate.now())) {
                    System.out.println("Due date cannot be in the past. Please try again.");
                } else {
                    return dueDate;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
    }

    private static void viewAllTasks() {
        System.out.println("\n--- All Tasks ---");
        List<Task> tasks = taskService.getAllTasks();
        displayTasks(tasks);
    }

    private static void viewTasksByPriority() {
        TaskPriority priority = selectPriority();
        System.out.println("\n--- Tasks with Priority: " + priority + " ---");
        List<Task> tasks = taskService.getTasksByPriority(priority);
        displayTasks(tasks);
    }

    private static void viewPendingTasks() {
        System.out.println("\n--- Pending Tasks ---");
        List<Task> tasks = taskService.getPendingTasks();
        displayTasks(tasks);
    }

    private static void markTaskCompleted() {
        System.out.println("\n--- Mark Task as Completed ---");
        System.out.print("Enter task ID: ");
        
        try {
            int taskId = Integer.parseInt(scanner.nextLine());
            if (taskService.markTaskCompleted(taskId)) {
                System.out.println("Task marked as completed!");
            } else {
                System.out.println("Task not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid task ID.");
        }
    }

    private static void deleteTask() {
        System.out.println("\n--- Delete Task ---");
        System.out.print("Enter task ID: ");
        
        try {
            int taskId = Integer.parseInt(scanner.nextLine());
            if (taskService.deleteTask(taskId)) {
                System.out.println("Task deleted successfully!");
            } else {
                System.out.println("Task not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid task ID.");
        }
    }

    private static void showStatistics() {
        System.out.println("\n--- Statistics ---");
        System.out.println("Total tasks: " + taskService.getTaskCount());
        System.out.println("Completed tasks: " + taskService.getCompletedTaskCount());
        System.out.println("Pending tasks: " + (taskService.getTaskCount() - taskService.getCompletedTaskCount()));
    }

    private static void displayTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }
        
        for (Task task : tasks) {
            System.out.println(task);
        }
    }
}