import java.util.Scanner;

public class InputUtils {
    private static final Scanner scanner = new Scanner(System.in);
    
    public static String getValidStringInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Error: Input cannot be empty. Please try again.");
        }
    }
    
    public static int getIntInput(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int input = Integer.parseInt(scanner.nextLine().trim());
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.println("Error: Please enter a number between " + min + " and " + max);
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            }
        }
    }
    
    public static double getDoubleInput(String prompt, double min) {
        while (true) {
            try {
                System.out.print(prompt);
                double input = Double.parseDouble(scanner.nextLine().trim());
                if (input >= min) {
                    return input;
                }
                System.out.println("Error: Value must be at least " + min);
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            }
        }
    }
}
