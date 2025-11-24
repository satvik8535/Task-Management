package service;

import model.Task;
import model.TaskPriority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        // Using a test-specific file or in-memory implementation would be better
        // For this example, we'll use the regular service
        taskService = new TaskService();
    }

    @Test
    void testAddValidTask() {
        boolean result = taskService.addTask(
            "Test Task", 
            "Test Description", 
            TaskPriority.MEDIUM, 
            LocalDate.now().plusDays(1)
        );
        assertTrue(result);
        assertEquals(1, taskService.getTaskCount());
    }

    @Test
    void testAddTaskWithInvalidTitle() {
        boolean result = taskService.addTask(
            "",  // Invalid empty title
            "Description", 
            TaskPriority.MEDIUM, 
            LocalDate.now().plusDays(1)
        );
        assertFalse(result);
    }

    @Test
    void testMarkTaskCompleted() {
        taskService.addTask("Test Task", "Desc", TaskPriority.LOW, LocalDate.now().plusDays(1));
        boolean result = taskService.markTaskCompleted(1);
        assertTrue(result);
        assertEquals(1, taskService.getCompletedTaskCount());
    }

    @Test
    void testGetPendingTasks() {
        taskService.addTask("Task 1", "Desc", TaskPriority.LOW, LocalDate.now().plusDays(1));
        taskService.addTask("Task 2", "Desc", TaskPriority.HIGH, LocalDate.now().plusDays(2));
        taskService.markTaskCompleted(1);
        
        List<Task> pendingTasks = taskService.getPendingTasks();
        assertEquals(1, pendingTasks.size());
        assertEquals("Task 2", pendingTasks.get(0).getTitle());
    }
}