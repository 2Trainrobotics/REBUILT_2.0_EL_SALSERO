package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANPIDController;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {

    private final CANSparkMax m_leftShooter = new CANSparkMax(ShooterConstants.kLeftShooterCanId, MotorType.kBrushless);
    private final CANSparkMax m_rightShooter = new CANSparkMax(ShooterConstants.kRightShooterCanId, MotorType.kBrushless);

    private final RelativeEncoder m_leftEncoder;
    private final RelativeEncoder m_rightEncoder;

    private final CANPIDController m_leftController;
    private final CANPIDController m_rightController;

    public ShooterSubsystem() {
        // Get encoders and controllers
        m_leftEncoder = m_leftShooter.getEncoder();
        m_rightEncoder = m_rightShooter.getEncoder();

        m_leftController = m_leftShooter.getPIDController();
        m_rightController = m_rightShooter.getPIDController();

        // Configure PID
        m_leftController.setP(ShooterConstants.kP);
        m_leftController.setI(ShooterConstants.kI);
        m_leftController.setD(ShooterConstants.kD);
        m_leftController.setFF(ShooterConstants.kFF);

        m_rightController.setP(ShooterConstants.kP);
        m_rightController.setI(ShooterConstants.kI);
        m_rightController.setD(ShooterConstants.kD);
        m_rightController.setFF(ShooterConstants.kFF);

        // Reset encoders to zero
        m_leftEncoder.setPosition(0);
        m_rightEncoder.setPosition(0);
    }

    /** Spin both shooter motors to target RPM */
    public void setShooterRPM(double rpm) {
        m_leftController.setReference(rpm, CANSparkMax.ControlType.kVelocity);
        m_rightController.setReference(rpm, CANSparkMax.ControlType.kVelocity);
    }

    /** Spin shooter to default RPM */
    public void spinShooter() {
        setShooterRPM(ShooterConstants.kDefaultShooterRPM);
    }

    /** Stop both shooter motors */
    public void stopShooter() {
        m_leftShooter.set(0);
        m_rightShooter.set(0);
    }

    /** Get current left motor RPM */
    public double getLeftRPM() {
        return m_leftEncoder.getVelocity();
    }

    /** Get current right motor RPM */
    public double getRightRPM() {
        return m_rightEncoder.getVelocity();
    }
}
