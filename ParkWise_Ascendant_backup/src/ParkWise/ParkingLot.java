package ParkWise;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private int lotNumber;
    private String name;
    private String address;
    private String city;
    private int availableSpaces;
    
    // יחסים (Relationships) מהדיאגרמה: "contains"
    private List<PriceList> priceHistory;
    private List<Conveyor> conveyors; 

    public ParkingLot(int lotNumber, String name, String address, String city, int capacity) {
        this.lotNumber = lotNumber;
        this.name = name;
        this.address = address;
        this.city = city;
        this.availableSpaces = capacity;
        
        // אתחול הרשימות
        this.priceHistory = new ArrayList<>();
        this.conveyors = new ArrayList<>();
    }

    // הפונקציה לעדכון פרטים כפי שמופיעה בדיאגרמה
    public void updateDetails(String name, String address, String city) {
        this.name = name;
        this.address = address;
        this.city = city;
        System.out.println("\t[Entity] ParkingLot details updated: " + name + ", " + city);
    }

    // ניהול היחסים (הוספת מחירונים ומסועים)
    public void addPriceList(PriceList pl) {
        this.priceHistory.add(pl);
    }

    public void addConveyor(Conveyor c) {
        this.conveyors.add(c);
    }

    public List<PriceList> getPriceHistory() {
        return priceHistory;
    }

    public int getLotNumber() { return lotNumber; }
}
