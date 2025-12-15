package ParkWise;

import java.util.List;

public class AdminDashboardUI {
    
    private LotManagementController lotController;
    private ConveyorController conveyorController;

    public AdminDashboardUI(LotManagementController lc, ConveyorController cc) {
        this.lotController = lc;
        this.conveyorController = cc;
    }

    // --- Lot Management Methods ---
    
    public void updateLotDetails(int lotID, String name, String address, String city) {
        System.out.println("[UI] User clicked 'Update Lot Details'");
        lotController.setLotDetails(lotID, name, address, city);
    }

    public void requestPriceHistory(int lotID) {
        System.out.println("[UI] User requested Price History");
        List<PriceList> history = lotController.getPriceHistory(lotID);
        // הצגה למסך (הדמיה)
        if (history != null) {
            System.out.println("[UI] Displaying " + history.size() + " price records.");
        }
    }

    // --- Conveyor Methods (כפי שראינו קודם) ---
    
    public void turnOnConveyors() {
        System.out.println("[UI] User clicked 'Turn On Conveyors'");
        conveyorController.activateConveyors();
    }
    
    public void setConveyorWeight(int conveyorID, double newWeight) {
        conveyorController.updateMaxWeight(conveyorID, newWeight);
    }
}
