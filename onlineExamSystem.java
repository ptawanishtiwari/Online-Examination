import java.util.HashMap;
import java.util.Scanner;

class User {
    private String userId;
    private String password;
    private String name;
    private String email;

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public boolean authenticate(String userId, String password) {
        return this.userId.equals(userId) && this.password.equals(password);
    }

    public void updateProfile(String name, String email) {
        this.name = name;
        this.email = email;
        System.out.println("Profile updated successfully.");
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
        System.out.println("Password updated successfully.");
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}

class ExamSystem {
    private HashMap<String, String> questions;
    private HashMap<String, String> answers;

    public ExamSystem() {
        questions = new HashMap<>();
        answers = new HashMap<>();
        loadQuestions();
    }

    private void loadQuestions() {
        questions.put("Q1: What is the capital of France?", "A. Paris  B. London  C. Berlin  D. Madrid");
        answers.put("Q1", "A");

        questions.put("Q2: What is 2 + 2?", "A. 3  B. 4  C. 5  D. 6");
        answers.put("Q2", "B");

        questions.put("Q3: Who wrote 'Hamlet'?", "A. Dickens  B. Shakespeare  C. Hemingway  D. Tolkien");
        answers.put("Q3", "B");
    }

    public void startExam(Scanner scanner, int timeLimit) {
        long startTime = System.currentTimeMillis();
        int score = 0;

        System.out.println("--- Exam Started ---");
        for (String question : questions.keySet()) {
            if ((System.currentTimeMillis() - startTime) / 1000 > timeLimit) {
                System.out.println("Time is up! Auto-submitting your answers.");
                break;
            }

            System.out.println(question);
            System.out.println(questions.get(question));
            System.out.print("Enter your answer (A/B/C/D): ");
            String userAnswer = scanner.nextLine().toUpperCase();

            if (userAnswer.equals(answers.get(question.substring(0, 2)))) {
                score++;
            }
        }

        System.out.println("--- Exam Ended ---");
        System.out.println("Your Score: " + score + " out of " + questions.size());
    }
}

public class OnlineExamSystem {
    private static User currentUser;
    private static Scanner scanner = new Scanner(System.in);
    private static ExamSystem examSystem = new ExamSystem();

    public static void main(String[] args) {
        User demoUser = new User("user123", "1234", "John Doe", "john@example.com");

        System.out.println("--- Welcome to Online Exam System ---");
        if (authenticateUser(demoUser)) {
            showMenu(demoUser);
        } else {
            System.out.println("Authentication failed. Exiting system.");
        }
    }

    private static boolean authenticateUser(User user) {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (user.authenticate(userId, password)) {
            currentUser = user;
            System.out.println("Login successful. Welcome, " + user.getName() + "!");
            return true;
        } else {
            return false;
        }
    }

    private static void showMenu(User user) {
        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Update Profile");
            System.out.println("2. Update Password");
            System.out.println("3. Start Exam");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    handleUpdateProfile(user);
                    break;
                case 2:
                    handleUpdatePassword(user);
                    break;
                case 3:
                    examSystem.startExam(scanner, 120); // 2-minute timer
                    break;
                case 4:
                    System.out.println("Logging out. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleUpdateProfile(User user) {
        System.out.print("Enter new name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new email: ");
        String email = scanner.nextLine();
        user.updateProfile(name, email);
    }

    private static void handleUpdatePassword(User user) {
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        user.updatePassword(newPassword);
    }
}
