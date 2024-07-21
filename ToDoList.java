import java.util.ArrayList;
import java.io.*;

public class ToDoList {
    private ArrayList<Task> tasks;

    public ToDoList() {
        tasks = new ArrayList<>();
    }

    public void addTask(String description) {
        tasks.add(new Task(description));
    }

    public void removeTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
        } else {
            System.out.println("Invalid task number.");
        }
    }

    public void markTaskCompleted(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).markCompleted();
        } else {
            System.out.println("Invalid task number.");
        }
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void displayTasks() {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    public void saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Task task : tasks) {
                writer.write(task.isCompleted() + "," + task.getDescription());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    public void loadFromFile(String filename) {
        tasks.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);
                boolean isCompleted = Boolean.parseBoolean(parts[0]);
                String description = parts[1];
                Task task = new Task(description);
                if (isCompleted) {
                    task.markCompleted();
                }
                tasks.add(task);
            }
        } catch (IOException e) {
            System.out.println("Error loading from file: " + e.getMessage());
        }
    }
}
