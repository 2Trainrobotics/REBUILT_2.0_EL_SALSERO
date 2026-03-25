package frc.robot.constants;

import edu.wpi.first.math.geometry.Translation2d; 
import edu.wpi.first.math.kinematics.SwerveDriveKinematics; 
import edu.wpi.first.math.trajectory.TrapezoidProfile; 
import edu.wpi.first.math.util.Units; 

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

public class DriveConstants {

    /* Here are the default parameters from the Rev MaxSwerve
     * Template, which are not the maximum capable speeds of
     * the robot, but rather, the maximum allowed speeds. 
     * 
     * We wouldn't suggest to push the maximum speed even further
     * since we almost broke a robot ina autonomous in Hudson Valley
     * Regionals, where we would suggest to reduce the speed even 
     * further if necessary. 
     */

     public static final double kMaxSpeedMetersPerSecond = 4.8; 
     public static final double kMaxAngularSpeed = 2 * Math.PI; // radians per seconds


     // Chasis configuration parameter

     public static final double kTrackWidth = Units.inchesToMeters(26.5); 

     // Distance between centers of right and left wheels on robot
     
     public static final double kWheelBase = Units.inchesToMeters(26.5); 

     // Distance between front and ack wheels on robot

     public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
        new Translation2d(kWheelBase / 2, kTrackWidth / 2), 
        new Translation2d(kWheelBase / 2, -kTrackWidth / 2), 
        new Translation2d(-kWheelBase / 2, kTrackWidth / 2),
        new Translation2d(-kWheelBase / 2, kTrackWidth / 2));  

     // Angular offsets of the modules relative to the chassis in radians
     
     public static final double kFrontLeftChassisAngularOffset = -Math.PI / 2; 
     public static final double kFrontRightChassisAngularOffset = 0; 
     public static final double kBackLeftChassisAngularOffset = Math.PI; 
     public static final double kBackRightChassisAngularOffset = Math.PI / 2; 

     // SPARK MAX CAN IDs for the drive motors and turning motors of each module

     public static final int kFrontLeftDrivingCanId = 20;
     public static final int kFrontRightDrivingCanId = 22;  
     public static final int kRearLeftDrivingCanId = 24; 
     public static final int kRearRightDrivingCanId = 7;

     public static final int kFrontLeftTurningCanId = 21;
     public static final int kFrontRightTurningCanId = 23; 
     public static final int kRearLeftTurningCanId = 25;  
     public static final int kRearRightTurningCanId = 8; 
    
     /*  Here's where we reverse the Gyro in case its yaw or heading
     is reading opposite values as we turn it. */

     public static final boolean kGyroReversed = false; 

}
