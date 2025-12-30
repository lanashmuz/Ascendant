package ParkWise;

public class ConveyorController {
    
    // הבקר מחזיק את היישות שהוא מנהל
    private Conveyor conveyor;

    public ConveyorController(Conveyor conveyor) {
        this.conveyor = conveyor;
    }

    // --- Methods from Diagram ---

    // פעולה שמתחילה את התהליך (מקביל ל-turnOnConveyors)
    public void activateConveyors() {
        if (conveyor.getStatus() == ConveyorStatus.OFF) {
            System.out.println("Controller: Activating conveyor " + conveyor.getConveyorID());
            conveyor.resetFailureCount();
            
            // תחילת תהליך הבדיקות
            executeIntegrityChecks(conveyor.getConveyorID());
        } else {
            System.out.println("Controller: Cannot activate. System is not OFF.");
        }
    }

    public void executeIntegrityChecks(int conveyorID) {
        // שלב 1: בדיקה מכאנית
        conveyor.setStatus(ConveyorStatus.CHECKING_MECH);
        System.out.println("Controller: Status changed to CHECKING_MECH");
        
        boolean mechResult = conveyor.runMechanicalCheck();

        if (mechResult) {
            // שלב 2: בדיקה אלקטרונית (רק אם המכאנית עברה)
            conveyor.setStatus(ConveyorStatus.CHECKING_ELEC);
            System.out.println("Controller: Status changed to CHECKING_ELEC");
            
            boolean elecResult = conveyor.runElectronicCheck();
            
            if (elecResult) {
                // הכל תקין -> עוברים למצב מבצעי
                conveyor.setStatus(ConveyorStatus.OPERATIONAL);
                System.out.println("Controller: All checks passed. System OPERATIONAL.");
            } else {
                handleCheckFailure(conveyorID);
            }
        } else {
            handleCheckFailure(conveyorID);
        }
    }

    public void handleCheckFailure(int conveyorID) {
        conveyor.incrementFailureCount();
        System.out.println("Controller: Check Failed! Failure count: " + conveyor.getFailureCount());
        
        if (conveyor.getFailureCount() >= 3) {
            conveyor.setPauseMode();
            System.out.println("Controller: Max failures reached. Sending alert.");
        } else {
            System.out.println("Controller: Retrying checks (Simulating logic)...");
            // כאן בעתיד יהיה הטיימר
        }
    }

    public void updateMaxWeight(int conveyorID, double weight) {
        // הבקר מקבל את הבקשה ומעביר ליישות
        System.out.println("Controller: Request to update weight received.");
        conveyor.setWeight(weight);
    }
    
    // פונקציה המדמה קבלת רכב והפעלת המסוע
    public void assignConveyor(String vehicleDetails) {
        if (conveyor.getStatus() == ConveyorStatus.OPERATIONAL) {
            conveyor.setStatus(ConveyorStatus.PAUSED_BUSY);
            System.out.println("Controller: Vehicle assigned. Conveyor is BUSY.");
            conveyor.moveVehicle();
            
            // בסיום הפעולה (דמי)
            conveyor.setStatus(ConveyorStatus.OPERATIONAL);
            System.out.println("Controller: Move complete. Conveyor back to OPERATIONAL.");
        } else {
            System.out.println("Controller: Cannot assign vehicle. Conveyor not OPERATIONAL.");
        }
    }
}