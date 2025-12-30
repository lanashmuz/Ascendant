package ParkWise;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ParkingLotView extends JPanel {
    private JTable lotTable;
    private DefaultTableModel tableModel;

    public ParkingLotView() {
        setLayout(new BorderLayout());
        
        // --- Header ---
        JLabel headerLabel = new JLabel("Parking Lot Management", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(headerLabel, BorderLayout.NORTH);

        // --- Table Section ---
        // Column names for the GUI
        String[] columnNames = {"ID", "Lot Name", "Address", "City", "Available Spaces"};
        
        // Custom Model to make ID read-only, but other fields editable
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // The ID (column 0) should not be changed
            }
        };

        lotTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(lotTable);
        add(scrollPane, BorderLayout.CENTER);

        // --- Buttons Section ---
        JPanel buttonPanel = new JPanel();
        JButton btnLoad = new JButton("Load Data");
        JButton btnSave = new JButton("Save Changes");

        buttonPanel.add(btnLoad);
        buttonPanel.add(btnSave);
        add(buttonPanel, BorderLayout.SOUTH);

        // --- Action Listeners ---

        btnLoad.addActionListener(e -> loadData());

        btnSave.addActionListener(e -> saveData());
        
        // Load automatically on open
        loadData();
    }

    private void loadData() {
        tableModel.setRowCount(0); // Clear table
        List<Object[]> rows = DatabaseManager.getAllParkingLots();
        for (Object[] row : rows) {
            tableModel.addRow(row);
        }
    }

    private void saveData() {
        int rowCount = tableModel.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            try {
                // Get data from table
                int id = Integer.parseInt(tableModel.getValueAt(i, 0).toString());
                String name = (String) tableModel.getValueAt(i, 1);
                String address = (String) tableModel.getValueAt(i, 2);
                String city = (String) tableModel.getValueAt(i, 3);
                int spaces = Integer.parseInt(tableModel.getValueAt(i, 4).toString());

                // Send to Database
                DatabaseManager.updateParkingLot(id, name, address, city, spaces);
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error saving row " + (i+1) + ": " + ex.getMessage());
            }
        }
        JOptionPane.showMessageDialog(this, "Parking Lots saved successfully!");
    }
}