package ParkWise;

public class AdminDashboardUI {
	public class Main {
	    public static void main(String[] args) {
	        // 1. יצירת היישות (הדאטה)
	        Conveyor myConveyor = new Conveyor(101, 1, 10, 20);

	        // 2. יצירת הבקר וחיבורו ליישות
	        ConveyorController controller = new ConveyorController(myConveyor);

	        // --- סימולציה של פעולות משתמש (Boundary Actions) ---
	        
	        System.out.println("--- Scenario 1: Admin turns on the system ---");
	        // המשתמש לוחץ על "Turn On" -> ה-UI קורא לקונטרולר
	        controller.activateConveyors();

	        System.out.println("\n--- Scenario 2: Admin updates weight config ---");
	        controller.updateMaxWeight(101, 500.0);

	        System.out.println("\n--- Scenario 3: Car arrives (System Operation) ---");
	        controller.assignConveyor("Mazda 3");
	    }
	}
}
