
package frc.robot.constants;

public class ShooterConstants {
    // CAN IDs for the Spark Flex shooter motors
    public static final int kLeftShooterCanId = 9;
    public static final int kRightShooterCanId = 10;

    // Default shooter target RPM
    public static final double kDefaultShooterRPM = 3000;

    // PID constants for velocity control (tune as needed)
    public static final double kP = 0.0005;
    public static final double kI = 0.0;
    public static final double kD = 0.0;
    public static final double kFF = 0.0002; // Feedforward (optional)
}
