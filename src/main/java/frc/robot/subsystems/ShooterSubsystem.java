package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkBase.IdleMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {

    private final CANSparkMax shooterLeft  = new CANSparkMax(ShooterConstants.kLeftShooterCanId, MotorType.kBrushless);
    private final CANSparkMax shooterRight = new CANSparkMax(ShooterConstants.kRightShooterCanId, MotorType.kBrushless);

    public ShooterSubsystem() {
        // Setup motors
        shooterLeft.setIdleMode(IdleMode.kCoast);
        shooterRight.setIdleMode(IdleMode.kCoast);

        // Make the right motor follow the left motor
        shooterRight.follow(shooterLeft, true);
    }

    /** Set shooter speed from 0.0 to 1.0 */
    public void setShooter(double speed) {
        shooterLeft.set(speed);
    }

    /** Stop the shooter */
    public void stop() {
        shooterLeft.set(0);
    }
}
