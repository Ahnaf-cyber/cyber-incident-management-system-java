import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        // Shared IncidentManager for both UI types
        IncidentManager manager = new IncidentManager();
        Scanner sc = new Scanner(System.in);

        // UI selection menu
        System.out.println("Choose Interface:");
        System.out.println("(1) Text Based");
        System.out.println("(2) GUI");

        int choice = sc.nextInt();

        // Launch selected interface
        if (choice == 1) {
            new ConsoleUI(manager).start();
        } else {
            new GUIFrame(manager);
        }
    }
}
