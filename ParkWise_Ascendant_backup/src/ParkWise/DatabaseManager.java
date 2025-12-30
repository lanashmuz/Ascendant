package ParkWise;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
	private static final String DB_URL = "jdbc:ucanaccess://ex1_access_2026_Acendent.accdb";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // Method to insert a single price list row into Access
    public static void insertPriceList(int id, int year, double first, double add, double extra, double full) {
        String sql = "INSERT INTO PriceList (PriceListID, yearAssociated, firstHour, additionalHour, extraHour, fullDay) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.setInt(2, year);
            pstmt.setDouble(3, first);
            pstmt.setDouble(4, add);
            pstmt.setDouble(5, extra);
            pstmt.setDouble(6, full);
            
            pstmt.executeUpdate();
            System.out.println("Saved PriceList ID: " + id);
            
        } catch (SQLException e) {
            // Ignore duplicate key errors if the ID already exists
            if(e.getErrorCode() == -104) { 
                System.out.println("PriceList ID " + id + " already exists. Skipping.");
            } else {
                e.printStackTrace();
            }
        }
    }

    // Method to read all data from Access to display in the table
    public static List<Object[]> getAllPriceLists() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT * FROM PriceList";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Object[]{
                    rs.getInt("PriceListID"),
                    rs.getInt("yearAssociated"),
                    rs.getDouble("firstHour"),
                    rs.getDouble("additionalHour"),
                    rs.getDouble("extraHour"),
                    rs.getDouble("fullDay")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
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
    // 1. READ: Fetch all conveyors for the GUI table
    public static List<Object[]> getAllConveyors() {
        List<Object[]> list = new ArrayList<>();
        // Make sure field names match your Access table exactly!
        String sql = "SELECT conveyerID, ParkinglotID, maxWeight FROM Conveyers";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Object[]{
                    rs.getInt("conveyerID"),
                    rs.getInt("ParkinglotID"),
                    rs.getDouble("maxWeight")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. EDIT: Update a specific conveyor's maxWeight
    public static void updateConveyor(int id, int parkingLotID, double maxWeight) {
        String sql = "UPDATE Conveyers SET maxWeight = ?, ParkinglotID = ? WHERE conveyerID = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, maxWeight);
            pstmt.setInt(2, parkingLotID);
            pstmt.setInt(3, id);
            
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Conveyor " + id + " updated successfully.");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 // ... inside DatabaseManager class ...

    // ==========================================
    // PARKING LOT MANAGEMENT
    // ==========================================

    // 1. READ: Fetch all Parking Lots
    public static List<Object[]> getAllParkingLots() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT ParkinglotID, lotName, adresse, city, availableSpaces FROM ParkingLots";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Object[]{
                    rs.getInt("ParkinglotID"),
                    rs.getString("lotName"),
                    rs.getString("adresse"),
                    rs.getString("city"),
                    rs.getInt("availableSpaces")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. EDIT: Update a specific Parking Lot
    public static void updateParkingLot(int id, String name, String address, String city, int spaces) {
        String sql = "UPDATE ParkingLots SET lotName = ?, adresse = ?, city = ?, availableSpaces = ? WHERE ParkinglotID = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, name);
            pstmt.setString(2, address);
            pstmt.setString(3, city);
            pstmt.setInt(4, spaces);
            pstmt.setInt(5, id);
            
            pstmt.executeUpdate();
            System.out.println("Parking Lot " + id + " updated.");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 // ... inside DatabaseManager class ...

    // 3. CREATE: Add a new Conveyor
 // Add this to DatabaseManager.java
    public static boolean insertConveyor(int parkingLotID, double maxWeight) {
        // Note: conveyerID is omitted so Access generates it automatically
        String sql = "INSERT INTO Conveyers (ParkinglotID, maxWeight) VALUES (?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, parkingLotID);
            pstmt.setDouble(2, maxWeight);
            
            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
