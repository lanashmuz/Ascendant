package ParkWise;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PricingView extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;

    public PricingView() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        JButton btnImport = new JButton("Import JSON");
        JButton btnRefresh = new JButton("Refresh from DB");

        topPanel.add(btnImport);
        topPanel.add(btnRefresh);
        add(topPanel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Year", "1st Hour", "Add. Hour", "Extra Hour", "Full Day"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // --- Import Button: Reads JSON, Updates Table AND Database ---
        btnImport.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(".");
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                importJsonData(fileChooser.getSelectedFile());
            }
        });

        // --- Refresh Button: Reloads from Database ---
        btnRefresh.addActionListener(e -> loadDataFromDB());
        
        // Initial load
        loadDataFromDB();
    }

    private void loadDataFromDB() {
        tableModel.setRowCount(0); // Clear table
        List<Object[]> rows = DatabaseManager.getAllPriceLists();
        for (Object[] row : rows) {
            tableModel.addRow(row);
        }
    }

    private void importJsonData(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) jsonContent.append(line.trim());

            Pattern pattern = Pattern.compile("\\{([^}]*)\\}");
            Matcher matcher = pattern.matcher(jsonContent.toString());

            int count = 0;
            while (matcher.find()) {
                String obj = matcher.group(1);
                
                String idStr = getValue(obj, "priceListID");
                
                if (idStr != null) {
                    // Parse values
                    int id = Integer.parseInt(idStr);
                    int year = Integer.parseInt(getValue(obj, "yearAssociated"));
                    double first = Double.parseDouble(getValue(obj, "firstHour"));
                    double add = Double.parseDouble(getValue(obj, "additionalHour"));
                    double extra = Double.parseDouble(getValue(obj, "extraHour"));
                    double full = Double.parseDouble(getValue(obj, "fullDay"));

                    // 1. Insert into Database
                    DatabaseManager.insertPriceList(id, year, first, add, extra, full);
                    count++;
                }
            }
            
            // 2. Refresh UI from Database to show new data
            loadDataFromDB();
            JOptionPane.showMessageDialog(this, "Successfully imported and saved " + count + " items.");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private String getValue(String source, String key) {
        String searchKey = "\"" + key + "\":";
        int start = source.indexOf(searchKey);
        if (start == -1) return null;
        start += searchKey.length();
        int end = source.indexOf(",", start);
        if (end == -1) end = source.length();
        return source.substring(start, end).trim();
    }
}
