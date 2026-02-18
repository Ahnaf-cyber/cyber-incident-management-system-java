// Model class representing a Cyber Security Incident.
 
public class Incident {

    private int id;
    private String customerName;
    private String incidentType;
    private String severity;
    private String status;
    private String description;

    // Constructor
    public Incident(int id, String customerName, String incidentType,
                    String severity, String status, String description) {
        this.id = id;
        this.customerName = customerName;
        this.incidentType = incidentType;
        this.severity = severity;
        this.status = status;
        this.description = description;
    }

    // Getters
    public int getId() { return id; }
    public String getCustomerName() { return customerName; }
    public String getIncidentType() { return incidentType; }
    public String getSeverity() { return severity; }
    public String getStatus() { return status; }
    public String getDescription() { return description; }

    // Setters
    public void setCustomerName(String name) { this.customerName = name; }
    public void setIncidentType(String type) { this.incidentType = type; }
    public void setSeverity(String severity) { this.severity = severity; }
    public void setStatus(String status) { this.status = status; }
    public void setDescription(String description) { this.description = description; }

    // Converts incident to file format
    public String toFileString() {
        return "ID: " + id +
                "; Name: " + customerName +
                "; Incident type: " + incidentType +
                "; Severity: " + severity +
                "; Status: " + status +
                "; Description: " + description;
    }

    // Recreates incident from file line
    public static Incident fromFileString(String line) {
        String[] parts = line.split(";");

        int id = Integer.parseInt(parts[0].split(":")[1].trim());
        String name = parts[1].split(":")[1].trim();
        String type = parts[2].split(":")[1].trim();
        String severity = parts[3].split(":")[1].trim();
        String status = parts[4].split(":")[1].trim();
        String desc = parts[5].split(":")[1].trim();

        return new Incident(id, name, type, severity, status, desc);
    }

    // Display-friendly format
    @Override
    public String toString() {
        return "ID: " + id +
                " | Name: " + customerName +
                " | Type: " + incidentType +
                " | Severity: " + severity +
                " | Status: " + status +
                " | Description: " + description;
    }
}
