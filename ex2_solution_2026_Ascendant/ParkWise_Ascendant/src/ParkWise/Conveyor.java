package ParkWise;

import java.util.Date;

public class Conveyor {
    // שדות (Fields) לפי הדיאגרמה
    private int conveyorID;
    private double maxWeightCapacity;
    private ConveyorStatus status;
    private int failureCount; // החליף את attemptCount
    private Date integrityTimer;
    private int floor;
    private int x_coord;
    private int y_coord;

    // שדות שהוזזו (Moved Fields)
    private double pendingMaxWeight;
    private boolean isConfigurationConfirmed;
    private boolean mechCheckPassed;
    private boolean elecCheckPassed;

    // בנאי (Constructor)
    public Conveyor(int id, int floor, int x, int y) {
        this.conveyorID = id;
        this.floor = floor;
        this.x_coord = x;
        this.y_coord = y;
        this.status = ConveyorStatus.OFF;
        this.failureCount = 0;
        this.maxWeightCapacity = 100.0;
    }

    // --- Methods defined in Diagram ---

    public boolean runMechanicalCheck() {
        System.out.println("\t[Entity] Running internal mechanical diagnostics...");
        // כאן תהיה בדיקת החומרה האמיתית בעתיד
        this.mechCheckPassed = true; // Hardcoded success for now
        return this.mechCheckPassed;
    }

    public boolean runElectronicCheck() {
        System.out.println("\t[Entity] Running internal electronic diagnostics...");
        this.elecCheckPassed = true; // Hardcoded success for now
        return this.elecCheckPassed;
    }

    public void setPauseMode() {
        this.status = ConveyorStatus.PAUSED_FAILURE;
        System.out.println("\t[Entity] Status set to PAUSED_FAILURE.");
    }

    public void setWeight(double weight) {
        this.pendingMaxWeight = weight;
        System.out.println("\t[Entity] Pending weight set to: " + weight + ". Waiting for confirmation.");
    }
    
    // פעולה להזזת רכב (דמי)
    public void moveVehicle() { 
        // הערה: ה-Route לא ממומש כרגע כי לא מופיע בדיאגרמה כמחלקה מלאה
        System.out.println("\t[Entity] Moving vehicle physical mechanism active...");
    }

    // Getters / Setters נחוצים
    public void setStatus(ConveyorStatus status) { this.status = status; }
    public ConveyorStatus getStatus() { return status; }
    public int getConveyorID() { return conveyorID; }
    public int getFailureCount() { return failureCount; }
    public void incrementFailureCount() { this.failureCount++; }
    public void resetFailureCount() { this.failureCount = 0; }
}
