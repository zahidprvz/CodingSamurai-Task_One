import java.text.SimpleDateFormat;
import java.util.*;

public class ToDoListApp {
    private List<Task> tasks;
    private Scanner scanner;
    private SimpleDateFormat dateFormat;

    public ToDoListApp() {
        tasks = new ArrayList<>();
        scanner = new Scanner(System.in);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    public void run() {
        System.out.println("Welcome to the Interactive To-Do List!");

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Add Task");
            System.out.println("2. Mark Task as Complete");
            System.out.println("3. Remove Task");
            System.out.println("4. View Tasks");
            System.out.println("5. Quit");

            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    markTaskAsComplete();
                    break;
                case 3:
                    removeTask();
                    break;
                case 4:
                    viewTasks();
                    break;
                case 5:
                    quit();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private int getUserChoice() {
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline
        return choice;
    }

    private void addTask() {
        System.out.print("Enter task description: ");
        String description = scanner.nextLine();

        System.out.print("Set a due date (yyyy-MM-dd, leave empty if none): ");
        String dateStr = scanner.nextLine();
        Date dueDate = parseDate(dateStr);

        System.out.print("Set task priority (High, Medium, Low, leave empty if none): ");
        String priority = scanner.nextLine();

        Task task = new Task(description, dueDate, priority);
        tasks.add(task);
        System.out.println("Task added: " + task.getDescription());
    }

    private Date parseDate(String dateStr) {
        try {
            if (!dateStr.isEmpty()) {
                return dateFormat.parse(dateStr);
            }
        } catch (Exception e) {
            System.out.println("Invalid date format. Using no due date.");
        }
        return null;
    }

    private void markTaskAsComplete() {
        viewTasks();
        System.out.print("Enter task index to mark as complete: ");
        int indexToMark = scanner.nextInt();

        if (indexToMark >= 0 && indexToMark < tasks.size()) {
            Task task = tasks.get(indexToMark);
            task.markAsComplete();
            System.out.println("Task marked as complete: " + task.getDescription());
        } else {
            System.out.println("Invalid index.");
        }
    }

    private void removeTask() {
        viewTasks();
        System.out.print("Enter task index to remove: ");
        int indexToRemove = scanner.nextInt();

        if (indexToRemove >= 0 && indexToRemove < tasks.size()) {
            Task removedTask = tasks.remove(indexToRemove);
            System.out.println("Task removed: " + removedTask.getDescription());
        } else {
            System.out.println("Invalid index.");
        }
    }

    private void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks.");
        } else {
            System.out.println("To-Do List:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(i + ". " + tasks.get(i));
            }
        }
    }

    private void quit() {
        System.out.println("Goodbye!");
        scanner.close();
        System.exit(0);
    }

    public static void main(String[] args) {
        ToDoListApp app = new ToDoListApp();
        app.run();
    }
}

class Task {
    private String description;
    private Date dueDate;
    private String priority;
    private boolean isCompleted;

    public Task(String description, Date dueDate, String priority) {
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.isCompleted = false;
    }

    public String getDescription() {
        return description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void markAsComplete() {
        isCompleted = true;
    }

    @Override
    public String toString() {
        StringBuilder taskStr = new StringBuilder();
        taskStr.append(isCompleted ? "[X] " : "[ ] ");
        taskStr.append(description);
        if (dueDate != null) {
            taskStr.append(" (Due: ").append(new SimpleDateFormat("yyyy-MM-dd").format(dueDate)).append(")");
        }
        if (!priority.isEmpty()) {
            taskStr.append(" [Priority: ").append(priority).append("]");
        }
        return taskStr.toString();
    }
}
