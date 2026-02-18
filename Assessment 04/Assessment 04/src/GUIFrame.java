import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.io.File;

public class GUIFrame extends JFrame {
    private IncidentManager manager;
    private final Font contentFont = new Font("Segoe UI", Font.BOLD, 16);

    public GUIFrame(IncidentManager manager) {
        this.manager = manager;
        
        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Segoe UI", Font.BOLD, 16)));
        UIManager.put("TextField.font", new FontUIResource(new Font("Segoe UI", Font.BOLD, 16)));

        setTitle("Cyber Security Incident Report");
        setSize(1300, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        ImageIcon bgIcon = loadResource("bg.gif");
        JLabel background = new JLabel(bgIcon);
        background.setLayout(new BorderLayout());
        background.setBackground(new Color(10, 15, 30));
        background.setOpaque(true);
        setContentPane(background);

        JPanel titleContainer = new JPanel(new BorderLayout());
        titleContainer.setOpaque(false);
        titleContainer.setBorder(BorderFactory.createEmptyBorder(40, 100, 20, 100));

        JLabel titleLabel = new JLabel("Cyber Security Incident Report", JLabel.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 150));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);
                g2.setColor(new Color(0, 255, 255));
                g2.setStroke(new BasicStroke(3));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 50, 50);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 55));
        titleLabel.setForeground(Color.LIGHT_GRAY);
        titleLabel.setPreferredSize(new Dimension(1000, 120));
        titleContainer.add(titleLabel, BorderLayout.CENTER);
        background.add(titleContainer, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new CircularLayout(250));
        buttonPanel.setOpaque(false);
        buttonPanel.add(createCircularButton("<html><center>Add<br>Incident</center></html>", e -> addIncidentPopup()));
        buttonPanel.add(createCircularButton("<html><center>Edit<br>Incident</center></html>", e -> editIncidentPopup()));
        buttonPanel.add(createCircularButton("Save", e -> { manager.saveToFile(); JOptionPane.showMessageDialog(this, "Saved Successfully"); }));
        buttonPanel.add(createCircularButton("Exit", e -> System.exit(0)));
        buttonPanel.add(createCircularButton("<html><center>Delete<br>Incident</center></html>", e -> deleteIncidentPopup()));
        buttonPanel.add(createCircularButton("<html><center>View<br>Incidents</center></html>", e -> viewPopup()));

        background.add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private ImageIcon loadResource(String filename) {
        String[] paths = { 
            filename, 
            "src/" + filename, 
            "bin/" + filename, 
            "F:/Assessment 04/Assessment 04/" + filename 
        };
        for (String p : paths) {
            File f = new File(p);
            if (f.exists()) return new ImageIcon(f.getAbsolutePath());
        }
        java.net.URL url = getClass().getResource("/" + filename);
        return (url != null) ? new ImageIcon(url) : null;
    }

    private JButton createCircularButton(String text, java.awt.event.ActionListener action) {
        JButton b = new JButton(text) {
            private boolean hovered = false;
            {
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hovered = true; repaint(); }
                    public void mouseExited(MouseEvent e) { hovered = false; repaint(); }
                });
            }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hovered ? new Color(0, 100, 200, 150) : new Color(15, 25, 45));
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.setColor(hovered ? Color.CYAN : new Color(0, 150, 255));
                g2.setStroke(new BasicStroke(hovered ? 4 : 2));
                g2.drawOval(2, 2, getWidth() - 5, getHeight() - 5);
                g2.dispose();
                super.paintComponent(g);
            }
            @Override
            public boolean contains(int x, int y) {
                return new Ellipse2D.Float(0, 0, getWidth(), getHeight()).contains(x, y);
            }
        };
        b.setFont(new Font("Segoe UI", Font.BOLD, 18));
        b.setForeground(Color.WHITE);
        b.setPreferredSize(new Dimension(160, 160));
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.addActionListener(action);
        return b;
    }

    private void addIncidentPopup() {
        JDialog dialog = new JDialog(this, "REGISTER NEW INCIDENT", true);
        dialog.setSize(750, 850);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;

        JTextField idField = new JTextField(); 
        JTextField nameField = new JTextField();
        JTextField typeField = new JTextField();
        JComboBox<String> sevBox = new JComboBox<>(new String[]{"Low", "Medium", "High"});
        JComboBox<String> statusBox = new JComboBox<>(new String[]{"Open", "Closed"});
        JTextArea descArea = new JTextArea(8, 20);
        descArea.setLineWrap(true); descArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(descArea);

        gbc.gridy = 0; panel.add(new JLabel("INCIDENT ID:"), gbc);
        gbc.gridy = 1; panel.add(idField, gbc);
        gbc.gridy = 2; panel.add(new JLabel("CUSTOMER NAME:"), gbc);
        gbc.gridy = 3; panel.add(nameField, gbc);
        gbc.gridy = 4; panel.add(new JLabel("TYPE:"), gbc);
        gbc.gridy = 5; panel.add(typeField, gbc);
        gbc.gridy = 6; panel.add(new JLabel("SEVERITY:"), gbc);
        gbc.gridy = 7; panel.add(sevBox, gbc);
        gbc.gridy = 8; panel.add(new JLabel("STATUS:"), gbc);
        gbc.gridy = 9; panel.add(statusBox, gbc);
        gbc.gridy = 10; panel.add(new JLabel("DESCRIPTION:"), gbc);
        gbc.gridy = 11; panel.add(scrollPane, gbc);

        applyGlassStyle(panel);

        JButton confirmBtn = new JButton("CONFIRM");
        confirmBtn.setFont(new Font("Segoe UI", Font.BOLD, 22));
        confirmBtn.setBackground(new Color(20, 40, 60));
        confirmBtn.setForeground(Color.WHITE);
        confirmBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                if (manager.idExists(id)) {
                    JOptionPane.showMessageDialog(dialog, "WARNING: Incident ID " + id + " already exists!", "Duplicate ID", JOptionPane.WARNING_MESSAGE);
                } else {
                    manager.addIncident(new Incident(id, nameField.getText(), typeField.getText(), sevBox.getSelectedItem().toString(), statusBox.getSelectedItem().toString(), descArea.getText()));
                    dialog.dispose();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter a valid Numeric ID.");
            }
        });

        setupPopupContent(dialog, panel, confirmBtn);
        dialog.setVisible(true);
    }

    private void editIncidentPopup() {
        String idStr = JOptionPane.showInputDialog(this, "Enter ID to Edit:");
        if (idStr == null || idStr.isEmpty()) return;
        try {
            Incident i = manager.searchById(Integer.parseInt(idStr));
            if (i == null) { JOptionPane.showMessageDialog(this, "Not Found!"); return; }

            JDialog dialog = new JDialog(this, "MODIFY INCIDENT #" + i.getId(), true);
            dialog.setSize(750, 850);
            dialog.setLocationRelativeTo(this);

            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5); gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
            
            JTextField name = new JTextField(i.getCustomerName());
            JTextField type = new JTextField(i.getIncidentType());
            JComboBox<String> sev = new JComboBox<>(new String[]{"Low", "Medium", "High"}); sev.setSelectedItem(i.getSeverity());
            JComboBox<String> status = new JComboBox<>(new String[]{"Open", "Closed"}); status.setSelectedItem(i.getStatus());
            JTextArea desc = new JTextArea(i.getDescription(), 8, 20);
            JScrollPane scrollPane = new JScrollPane(desc);

            gbc.gridy = 0; panel.add(new JLabel("UPDATE NAME:"), gbc);
            gbc.gridy = 1; panel.add(name, gbc);
            gbc.gridy = 2; panel.add(new JLabel("UPDATE TYPE:"), gbc);
            gbc.gridy = 3; panel.add(type, gbc);
            gbc.gridy = 4; panel.add(new JLabel("UPDATE SEVERITY:"), gbc);
            gbc.gridy = 5; panel.add(sev, gbc);
            gbc.gridy = 6; panel.add(new JLabel("UPDATE STATUS:"), gbc);
            gbc.gridy = 7; panel.add(status, gbc);
            gbc.gridy = 8; panel.add(new JLabel("UPDATE DESCRIPTION:"), gbc);
            gbc.gridy = 9; panel.add(scrollPane, gbc);

            applyGlassStyle(panel);
            JButton confirmBtn = new JButton("CONFIRM");
            confirmBtn.setFont(new Font("Segoe UI", Font.BOLD, 22));
            confirmBtn.setBackground(new Color(20, 40, 60));
            confirmBtn.setForeground(Color.WHITE);
            confirmBtn.addActionListener(e -> {
                manager.editIncident(i.getId(), name.getText(), type.getText(), sev.getSelectedItem().toString(), status.getSelectedItem().toString(), desc.getText());
                dialog.dispose();
            });

            setupPopupContent(dialog, panel, confirmBtn);
            dialog.setVisible(true);
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Invalid ID."); }
    }

    private void viewPopup() {
        JDialog dialog = new JDialog(this, "DATABASE RECORDS", true);
        dialog.setSize(750, 850);
        dialog.setLocationRelativeTo(this);

        JTextArea area = new JTextArea(15, 45);
        area.setEditable(false);
        area.setBackground(Color.WHITE);
        area.setForeground(Color.BLACK);
        area.setFont(new Font("Monospaced", Font.BOLD, 16));
        
        for (Incident i : manager.getIncidents()) { 
            area.append(i.toString() + "\n\n"); 
        }
        
        JPanel p = new JPanel(new BorderLayout());
        p.add(new JScrollPane(area));
        applyGlassStyle(p);

        JButton okBtn = new JButton("OK");
        okBtn.setFont(new Font("Segoe UI", Font.BOLD, 22));
        okBtn.setBackground(new Color(20, 40, 60));
        okBtn.setForeground(Color.WHITE);
        okBtn.addActionListener(e -> dialog.dispose());

        setupPopupContent(dialog, p, okBtn);
        dialog.setVisible(true);
    }

    private void deleteIncidentPopup() {
        String idStr = JOptionPane.showInputDialog(this, "Enter ID to Delete:");
        if (idStr != null && !idStr.isEmpty()) {
            try {
                boolean d = manager.deleteById(Integer.parseInt(idStr));
                JOptionPane.showMessageDialog(this, d ? "REMOVED" : "NOT FOUND");
            } catch (Exception e) { JOptionPane.showMessageDialog(this, "Invalid ID."); }
        }
    }

    private void applyGlassStyle(JPanel panel) {
        panel.setOpaque(true);
        panel.setBackground(new Color(0, 0, 0, 180)); 
        panel.setBorder(new CompoundBorder(new LineBorder(new Color(0, 255, 255), 2), new EmptyBorder(25, 25, 25, 25)));
        for (Component c : panel.getComponents()) {
            if (c instanceof JLabel) { c.setForeground(Color.CYAN); c.setFont(new Font("Segoe UI", Font.BOLD, 18)); }
            if (c instanceof JTextField || c instanceof JComboBox || c instanceof JScrollPane) {
                c.setBackground(Color.WHITE); c.setForeground(Color.BLACK); c.setFont(contentFont);
                if (c instanceof JComponent) ((JComponent)c).setBorder(new LineBorder(new Color(0, 200, 255), 1));
            }
            if (c instanceof JScrollPane) {
                JTextArea inner = (JTextArea)((JScrollPane)c).getViewport().getView();
                inner.setBackground(Color.WHITE); inner.setForeground(Color.BLACK); inner.setFont(new Font("Monospaced", Font.BOLD, 16));
            }
        }
    }

    private void setupPopupContent(JDialog dialog, JPanel mainPanel, JButton actionBtn) {
        
        ImageIcon popIcon = loadResource("popup.gif");
        JLabel bg = new JLabel(popIcon) {
            @Override
            protected void paintComponent(Graphics g) { 
                if (popIcon != null) {
                    g.drawImage(popIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        bg.setLayout(new GridBagLayout());
        dialog.setContentPane(bg);

        JPanel container = new JPanel(new BorderLayout(15, 15));
        container.setOpaque(false);
        container.add(mainPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        actionBtn.setPreferredSize(new Dimension(200, 45));
        bottomPanel.add(actionBtn);
        container.add(bottomPanel, BorderLayout.SOUTH);

        bg.add(container);
    }
}

class CircularLayout implements LayoutManager {
    private int radius;
    public CircularLayout(int r) { this.radius = r; }
    public void layoutContainer(Container p) {
        Component[] comps = p.getComponents();
        int centerX = p.getWidth() / 2, centerY = p.getHeight() / 2;
        for (int i = 0; i < comps.length; i++) {
            double angle = (2 * Math.PI * i / comps.length) - (Math.PI / 2);
            int x = (int) (centerX + radius * Math.cos(angle)) - comps[i].getPreferredSize().width / 2;
            int y = (int) (centerY + radius * Math.sin(angle)) - comps[i].getPreferredSize().height / 2;
            comps[i].setBounds(x, y, comps[i].getPreferredSize().width, comps[i].getPreferredSize().height);
        }
    }
    public Dimension preferredLayoutSize(Container p) { return p.getSize(); }
    public Dimension minimumLayoutSize(Container p) { return p.getSize(); }
    public void addLayoutComponent(String n, Component c) {}
    public void removeLayoutComponent(Component c) {}
}