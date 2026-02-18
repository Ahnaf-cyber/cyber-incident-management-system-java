import java.util.*;
import java.io.*;

/**
 * Handles all incident operations like
 * add, search, edit, delete, load and save.
 */
public class IncidentManager {

    // Stores all incidents in memory
    private ArrayList<Incident> incidents = new ArrayList<>();

    // File path where incidents are stored permanently
    private final String FILE = "F:/Assessment 04/Assessment 04/incidents.txt";

    // Constructor loads existing records from file
    public IncidentManager() {
        loadFromFile();
    }

    // Checks if an ID already exists
    public boolean idExists(int id) {
        return searchById(id) != null;
    }

    // Adds a new incident
    public void addIncident(Incident incident) {
        incidents.add(incident);
    }

    // Searches incident by ID
    public Incident searchById(int id) {
        for (Incident i : incidents) {
            if (i.getId() == id) return i;
        }
        return null;
    }

    // Updates incident details
    public boolean editIncident(int id, String name, String type,
                                String severity, String status, String desc) {
        Incident i = searchById(id);
        if (i == null) return false;

        i.setCustomerName(name);
        i.setIncidentType(type);
        i.setSeverity(severity);
        i.setStatus(status);
        i.setDescription(desc);
        return true;
    }

    // Deletes incident using ID
    public boolean deleteById(int id) {
        return incidents.removeIf(i -> i.getId() == id);
    }

    // Returns all incidents
    public ArrayList<Incident> getIncidents() {
        return incidents;
    }

    // Saves incidents to file
    public void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE))) {
            for (Incident i : incidents) {
                pw.println(i.toFileString());
            }
            System.out.println("Saved to: " + FILE);
        } catch (IOException e) {
            System.out.println("Save error: " + e.getMessage());
        }
    }

    // Loads incidents from file on startup
    private void loadFromFile() {
        File file = new File(FILE);
        if (!file.exists()) return;

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                incidents.add(Incident.fromFileString(sc.nextLine()));
            }
        } catch (Exception e) {
            System.out.println("Load error");
        }
    }
}
