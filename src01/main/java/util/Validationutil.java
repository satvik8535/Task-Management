package util;

import java.time.LocalDate;

public class ValidationUtil {
    
    public static boolean isValidTitle(String title) {
        return title != null && !title.trim().isEmpty() && title.length() <= 100;
    }
    
    public static boolean isValidDescription(String description) {
        return description == null || description.length() <= 500;
    }
    
    public static boolean isValidDate(LocalDate date) {
        return date != null && !date.isBefore(LocalDate.now());
    }
    
    public static boolean isValidId(int id) {
        return id > 0;
    }
}