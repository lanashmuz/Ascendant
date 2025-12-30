package ParkWise;

import javax.swing.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        // Basic window setup
        setTitle("ParkWise Management System");
        setSize(900, 600); // Increased width slightly for better table viewing
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        // Create the tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // 1. Pricing View
        tabbedPane.addTab("Pricing Management", new PricingView());
        
        // 2. Parking Lot View (NEW)
        tabbedPane.addTab("Parking Lots", new ParkingLotView());

        // 3. Conveyor View
        tabbedPane.addTab("Conveyor Control", new ConveyorView());

        add(tabbedPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainWindow().setVisible(true);
        });
    }
}