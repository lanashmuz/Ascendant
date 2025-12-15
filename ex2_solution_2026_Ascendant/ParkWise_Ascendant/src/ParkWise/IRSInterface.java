package ParkWise;

public class IRSInterface {
    
    private LotManagementController lotController;

    public IRSInterface(LotManagementController controller) {
        this.lotController = controller;
    }

    // הממשק מקבל נתונים ומעביר לקונטרולר
    public void receivePriceList(String jsonParams) {
        System.out.println("[IRS Interface] Received new price list data.");
        lotController.importPriceList(jsonParams);
    }
}
