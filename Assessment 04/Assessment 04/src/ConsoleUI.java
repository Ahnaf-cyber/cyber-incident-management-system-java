import java.util.Scanner;


 //Console-based interface for Incident Management.

public class ConsoleUI {

    private final IncidentManager manager;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleUI(IncidentManager manager) {
        this.manager = manager;
    }

    // Starts console menu loop
    public void start() {
        int choice = 0;
        while (choice != 4) {
            printMenu();
            choice = getIntInput("Selection: ");

            switch (choice) {
                case 1: addIncidentFlow(); break;
                case 2: viewIncidentsFlow(); break;
                case 3: searchByIdFlow(); break;
                case 4: System.out.println("Exiting and saving data..."); break;
                default: System.out.println("Invalid option."); break;
            }
        }
        manager.saveToFile();
    }

    // Adds new incident
    private void addIncidentFlow() {
        System.out.println("\n--- Register New Incident ---");
        int id = getIntInput("ID: ");
        System.out.print("Name: "); String name = scanner.nextLine();
        System.out.print("Type: "); String type = scanner.nextLine();
        System.out.print("Severity: "); String severity = scanner.nextLine();
        System.out.print("Status: "); String status = scanner.nextLine();
        System.out.print("Description: "); String desc = scanner.nextLine();

        manager.addIncident(new Incident(id, name, type, severity, status, desc));
        System.out.println("Incident added successfully!");
    }

    // Displays all incidents
    private void viewIncidentsFlow() {
        System.out.println("\n--- All Recorded Incidents ---");
        manager.getIncidents().forEach(System.out::println);
    }

    // Searches incident by ID
    private void searchByIdFlow() {
        int id = getIntInput("Enter ID: ");
        Incident found = manager.searchById(id);
        System.out.println(found != null ? found : "Not found");
    }

    // Menu display
    private void printMenu() {
        System.out.println("\n1. Add Incident");
        System.out.println("2. View Incidents");
        System.out.println("3. Search by ID");
        System.out.println("4. Exit");
    }

    // Safe integer input
    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Enter valid number.");
            scanner.next();
        }
        int val = scanner.nextInt();
        scanner.nextLine();
        return val;
    }
}
