package frc.robot.subsystems;

import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {

    private final SparkFlex m_leftShooter = new SparkFlex(ShooterConstants.kLeftShooterCanId);
    private final SparkFlex m_rightShooter = new SparkFlex(ShooterConstants.kRightShooterCanId);

    private final RelativeEncoder m_leftEncoder;
    private final RelativeEncoder m_rightEncoder;

    private final SparkClosedLoopController m_leftController;
    private final SparkClosedLoopController m_rightController;

    public ShooterSubsystem() {
        // Get encoders and controllers
        m_leftEncoder = m_leftShooter.getEncoder();
        m_rightEncoder = m_rightShooter.getEncoder();

        m_leftController = m_leftShooter.getClosedLoopController();
        m_rightController = m_rightShooter.getClosedLoopController();

        // Configure PID
        m_leftController.setPID(ShooterConstants.kP, ShooterConstants.kI, ShooterConstants.kD);
        m_rightController.setPID(ShooterConstants.kP, ShooterConstants.kI, ShooterConstants.kD);

        // Feedforward
        m_leftController.setFF(ShooterConstants.kFF);
        m_rightController.setFF(ShooterConstants.kFF);

        // Reset encoders to zero
        m_leftEncoder.setPosition(0);
        m_rightEncoder.setPosition(0);
    }

    /** Spin both shooter motors to target RPM */
    public void setShooterRPM(double rpm) {
        m_leftController.setSetpoint(rpm, ControlType.kVelocity);
        m_rightController.setSetpoint(rpm, ControlType.kVelocity);
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
