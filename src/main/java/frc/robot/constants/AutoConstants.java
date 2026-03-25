package frc.robot.constants;


import edu.wpi.first.math.trajectory.TrapezoidProfile; 


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

public class AutoConstants {
    public static final double kMaxSpeedMetersPerSecond = 3; 
    public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI; 
    public static final double kMaxAngularSpeedRAdiansPerSecond = Math.PI; 
    public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI; 

    public static final double kPXController = 1; 
    public static final double kPYController = 1; 
    public static final double kPThetaController = 1; 

    // Constraint for the motion profiled robot angle controller
    public static final TrapezoidProfile.Constraints kThetaControllerConstraints = new TrapezoidProfile.Constraints(
        kMaxAngularSpeedRadiansPerSecondSquared, kMaxAngularSpeedRAdiansPerSecond); 
}
