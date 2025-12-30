package ParkWise;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

public class LotManagementController {

    // הקונטרולר צריך להחזיק רשימה של חניונים שהוא מנהל
    private List<ParkingLot> parkingLots;

    public LotManagementController() {
        this.parkingLots = new ArrayList<>();
    }

    // פונקציית עזר להוספת חניון למערכת (כדי שיהיה עם מה לעבוד)
    public void registerParkingLot(ParkingLot lot) {
        this.parkingLots.add(lot);
    }

    // --- Methods from Diagram ---

    public void setLotDetails(int lotID, String name, String address, String city) {
        ParkingLot lot = findLotById(lotID);
        if (lot != null) {
            System.out.println("Controller: Requesting update for Lot " + lotID);
            lot.updateDetails(name, address, city);
        } else {
            System.out.println("Controller: Error - Lot " + lotID + " not found.");
        }
    }

    // קבלת JSON (כאן מיוצג כמחרוזת String לצורך פשטות)
    public void importPriceList(String jsonParams) {
        System.out.println("Controller: Importing Price List from external JSON...");
        // כאן אמור להיות Parsing של ה-JSON. 
        // נדמה יצירה של מחירון חדש:
        PriceList newPrice = new PriceList(2025, new Date(), 20.0, 5.0, 100.0);
        
        // נניח שהמחירון שייך לחניון הראשון ברשימה כברירת מחדל
        if (!parkingLots.isEmpty()) {
            parkingLots.get(0).addPriceList(newPrice);
            System.out.println("Controller: Price List added to Lot " + parkingLots.get(0).getLotNumber());
        }
    }

    public List<PriceList> getPriceHistory(int lotID) {
        ParkingLot lot = findLotById(lotID);
        if (lot != null) {
            System.out.println("Controller: Retrieving price history for Lot " + lotID);
            return lot.getPriceHistory();
        }
        return null;
    }

    // Private helper method
    private ParkingLot findLotById(int id) {
        for (ParkingLot lot : parkingLots) {
            if (lot.getLotNumber() == id) return lot;
        }
        return null;
    }
}
