package ParkWise;

public enum ConveyorStatus {
    OFF,
    CHECKING_MECH,
    CHECKING_ELEC,
    OPERATIONAL,    // מקביל ל-READY בדיאגרמה הקודמת
    PAUSED_FAILURE, // מקביל ל-PAUSE (תקלה)
    PAUSED_BUSY     // מקביל ל-EXECUTING_COMMAND (בזמן עבודה)
}
