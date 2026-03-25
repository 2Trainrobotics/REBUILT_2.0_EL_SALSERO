package frc.robot.constants;


/*Unlike how MaxSwerve-Java Template has setup DriveConstants, 
 * ModuleConstants, OIConstants, and AutoConstants all in one
 * constant class, I have decided to split them up into separate
 * classes for better organization. 
 * 
 * Constants classes are a great place to hold any numberical
 * or boolean constants that are used throughout the the robot
 * code, which would later be imported in other classes, 
 * such as subsystems, commands, and the RobotContainer. 
 */
public class ModuleConstants {

/* the MAXSwerve module can be configured with one of the three pinion 
 * gears: 12T, 13T, or 14T. This changes the drive speed of the module
The robot won't stop working if this is wrong, but, a pinion gear
with more teeth than the code will rseult in a robot that drives faster. */
    
public static final int kDrivingMotorPinionTeeth = 14; 

// Calculations required for drivign motor conversion fctors and feed forward
 public static final double kDrivingMotorFreeSpeedRps = NeoMotorConstants.kFreeSpeedRpm / 60;
 public static final double kWheelDiametersMeters = 0.0762; 
 public static final double kWheelCircumferenceMeters = kWheelDiametersMeters * Math.PI; 

 /* 45 teeth on the wheel's bevel gear, 22 teeth on the first-stage spur gear, 
 15 teeth on the bevel pinion. 
 */

 public static final double kDrivingMotorReduction = (45.0 * 22) / (kDrivingMotorPinionTeeth * 15); 
 public static final double kDrivingWheelFreeSpeedRps = (kDrivingMotorFreeSpeedRps * kWheelCircumferenceMeters)
    / kDrivingMotorReduction;

    // This class is declared inside ModuleConstants.java since 
    // the NeoMotorConstants are only used for the calculations of the driving motor constants,
    // and are not used anywhere else in the code, so it would be unnecessary to have a 
    // separate class for it.

    public static final class NeoMotorConstants {
    public static final int kFreeSpeedRpm = 5676; 
 }

}

