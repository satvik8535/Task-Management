package service;

import model.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    private static final String FILE_PATH = "data/tasks.txt";

    public void saveTasks(List<Task> tasks) {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs(); // Create directory if it doesn't exist
            
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                for (Task task : tasks) {
                    writer.println(task.toFileString());
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        }
    }

    public List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        File file = new File(FILE_PATH);
        
        if (!file.exists()) {
            return tasks;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    Task task = Task.fromFileString(line);
                    tasks.add(task);
                } catch (Exception e) {
                    System.err.println("Error parsing task: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading tasks: " + e.getMessage());
        }
        return tasks;
    }
}