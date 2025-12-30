package ParkWise;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ConveyorView extends JPanel {
    private JLabel statusLabel;
    private JTable conveyorTable;
    private DefaultTableModel tableModel;

    public ConveyorView() {
        setLayout(new BorderLayout());

        // ==========================================
        // TOP PANEL: Runtime Controls
        // ==========================================
        JPanel controlPanel = new JPanel(new GridBagLayout());
        controlPanel.setBorder(BorderFactory.createTitledBorder("Runtime Controls"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("Current Conveyor State:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        statusLabel = new JLabel("STOPPED");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 24));
        statusLabel.setForeground(Color.RED);

        gbc.gridx = 0; gbc.gridy = 0;
        controlPanel.add(titleLabel, gbc);
        gbc.gridy = 1;
        controlPanel.add(statusLabel, gbc);

        JPanel buttonPanel = new JPanel();
        JButton btnStart = new JButton("Start");
        JButton btnStop = new JButton("Stop");
        JButton btnMaintenance = new JButton("Maintenance");
        
        buttonPanel.add(btnStart);
        buttonPanel.add(btnStop);
        buttonPanel.add(btnMaintenance);

        gbc.gridy = 2;
        controlPanel.add(buttonPanel, gbc);

        add(controlPanel, BorderLayout.NORTH);

        // ==========================================
        // CENTER PANEL: Database Table
        // ==========================================
        JPanel databasePanel = new JPanel(new BorderLayout());
        databasePanel.setBorder(BorderFactory.createTitledBorder("Database Configuration"));

        String[] columnNames = {"Conveyor ID", "Parking Lot ID", "Max Weight"};
        
        // ID is not editable, others are
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; 
            }
        };

        conveyorTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(conveyorTable);
        databasePanel.add(scrollPane, BorderLayout.CENTER);

        // --- Buttons ---
        JPanel dbButtonPanel = new JPanel();
        JButton btnLoad = new JButton("Load Data from DB");
        JButton btnAdd = new JButton("Add Conveyor");
        JButton btnSave = new JButton("Save Changes to DB");
        
        dbButtonPanel.add(btnLoad);
        dbButtonPanel.add(btnAdd);
        dbButtonPanel.add(btnSave);
        databasePanel.add(dbButtonPanel, BorderLayout.SOUTH);

        add(databasePanel, BorderLayout.CENTER);

        // ==========================================
        // LOGIC & LISTENERS
        // ==========================================

        btnStart.addActionListener(e -> updateStatus("RUNNING"));
        btnStop.addActionListener(e -> updateStatus("STOPPED"));
        btnMaintenance.addActionListener(e -> updateStatus("MAINTENANCE"));

        btnLoad.addActionListener(e -> loadDataFromDB());
        btnAdd.addActionListener(e -> showAddConveyorDialog()); // Opens the modified dialog
        btnSave.addActionListener(e -> saveDataToDB());
        
        loadDataFromDB();
    }

    private void updateStatus(String newStatus) {
        statusLabel.setText(newStatus);
        if (newStatus.equals("RUNNING")) {
            statusLabel.setForeground(Color.GREEN);
        } else if (newStatus.equals("STOPPED")) {
            statusLabel.setForeground(Color.RED);
        } else {
            statusLabel.setForeground(Color.ORANGE);
        }
    }

    private void loadDataFromDB() {
        tableModel.setRowCount(0);
        List<Object[]> rows = DatabaseManager.getAllConveyors();
        for (Object[] row : rows) {
            tableModel.addRow(row);
        }
    }

    private void saveDataToDB() {
        int rowCount = tableModel.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            try {
                int id = Integer.parseInt(tableModel.getValueAt(i, 0).toString());
                int parkingID = Integer.parseInt(tableModel.getValueAt(i, 1).toString());
                double weight = Double.parseDouble(tableModel.getValueAt(i, 2).toString());
                
                DatabaseManager.updateConveyor(id, parkingID, weight);
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error parsing data at row " + (i+1));
            }
        }
        JOptionPane.showMessageDialog(this, "Changes saved to Database!");
    }

    // ==========================================
    // MODIFIED POPUP DIALOG (No ID Field)
    // ==========================================
    private void showAddConveyorDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add New Conveyor", true);
        dialog.setLayout(new GridLayout(3, 2, 10, 10)); // Reduced rows from 4 to 3
        dialog.setSize(300, 150); // Reduced height
        dialog.setLocationRelativeTo(this);

        JTextField txtParkingId = new JTextField();
        JTextField txtWeight = new JTextField();

        // Only ask for Parking Lot and Weight (ID is auto-generated)
        dialog.add(new JLabel("  Parking Lot ID:"));
        dialog.add(txtParkingId);
        
        dialog.add(new JLabel("  Max Weight:"));
        dialog.add(txtWeight);

        JButton btnConfirm = new JButton("Add");
        
        btnConfirm.addActionListener(ev -> {
            try {
                int pid = Integer.parseInt(txtParkingId.getText().trim());
                double weight = Double.parseDouble(txtWeight.getText().trim());

                // CALLING METHOD WITHOUT ID
                // Ensure you have updated DatabaseManager to accept just (int, double)
                boolean success = DatabaseManager.insertConveyor(pid, weight);

                if (success) {
                    loadDataFromDB(); 
                    dialog.dispose();
                    JOptionPane.showMessageDialog(this, "Conveyor added successfully!");
                } else {
                    JOptionPane.showMessageDialog(dialog, "Error adding conveyor.", "Database Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter valid numbers.", "Input Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        dialog.add(new JLabel("")); 
        dialog.add(btnConfirm);

        dialog.setVisible(true);
    }
}