package frc.robot.subsystems;

// import com.revrobotics.spark.SparkBase.ControlType;
// import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {

    private final SparkMax m_frontLeftShooter = new SparkMax(ShooterConstants.kFrontLeftShooterCanId, MotorType.kBrushless);
    private final SparkMax m_frontRightShooter = new SparkMax(ShooterConstants.kFrontRightShooterCanId, MotorType.kBrushless);
    private final SparkFlex m_rearLeftShooter = new SparkFlex(ShooterConstants.kRearLeftShooterCanId, MotorType.kBrushless);
    private final SparkFlex m_rearRightShooter = new SparkFlex(ShooterConstants.kRearRightShooterCanId, MotorType.kBrushless);

    // private final RelativeEncoder m_frontLeftEncoder;
    // private final RelativeEncoder m_frontRightEncoder;
    // private final RelativeEncoder m_rearLeftEncoder;
    // private final RelativeEncoder m_rearRightEncoder;

    // private final SparkClosedLoopController m_frontLeftController;
    // private final SparkClosedLoopController m_frontRightController;
    // private final SparkClosedLoopController m_rearLeftController;
    // private final SparkClosedLoopController m_rearRightController;

    public ShooterSubsystem() {
        // Setup motors
        // Make the right motors follow their corresponding left motor
        SparkMaxConfig frontLeftShooterConfig = new SparkMaxConfig();
        frontLeftShooterConfig.idleMode(SparkBaseConfig.IdleMode.kCoast).inverted(false);
        SparkMaxConfig frontRightShooterConfig = new SparkMaxConfig();
        frontRightShooterConfig.idleMode(SparkBaseConfig.IdleMode.kCoast).inverted(true).follow(m_frontLeftShooter, true);
        m_frontLeftShooter.configure(frontLeftShooterConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        m_frontRightShooter.configure(frontRightShooterConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        SparkFlexConfig rearLeftShooterConfig = new SparkFlexConfig();
        rearLeftShooterConfig.idleMode(SparkBaseConfig.IdleMode.kCoast).inverted(false);
        SparkFlexConfig rearRightShooterConfig = new SparkFlexConfig();
        rearRightShooterConfig.idleMode(SparkBaseConfig.IdleMode.kCoast).inverted(true).follow(m_rearLeftShooter, true);
        m_rearLeftShooter.configure(rearLeftShooterConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        m_rearRightShooter.configure(rearRightShooterConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        // Get encoders and controllers
        // m_frontLeftEncoder = m_frontLeftShooter.getEncoder();
        // m_frontRightEncoder = m_frontRightShooter.getEncoder();
        // m_rearLeftEncoder = m_rearLeftShooter.getEncoder();
        // m_rearRightEncoder = m_rearRightShooter.getEncoder();

        // Reset encoders to zero
        // m_frontLeftEncoder.setPosition(0);
        // m_frontRightEncoder.setPosition(0);
        // m_rearLeftEncoder.setPosition(0);
        // m_rearRightEncoder.setPosition(0);

        // m_frontLeftController = m_frontLeftShooter.getClosedLoopController();
        // m_frontRightController = m_frontRightShooter.getClosedLoopController();
        // m_rearLeftController = m_rearLeftShooter.getClosedLoopController();
        // m_rearRightController = m_rearRightShooter.getClosedLoopController();

        // Configure PID
        // m_frontLeftController.setP(ShooterConstants.kP);
        // m_frontLeftController.setI(ShooterConstants.kI);
        // m_frontLeftController.setD(ShooterConstants.kD);
        // m_frontLeftController.setFF(ShooterConstants.kFF);

        // m_frontRightController.setP(ShooterConstants.kP);
        // m_frontRightController.setI(ShooterConstants.kI);
        // m_frontRightController.setD(ShooterConstants.kD);
        // m_frontRightController.setFF(ShooterConstants.kFF);
    }

    /** Set shooter speed from 0.0 to 1.0 */
    public void setFrontShooterSpeed(double speed) {
        m_frontLeftShooter.set(speed);
        // m_frontRightShooter.set(speed);
    }

    public void setRearShooterSpeed(double speed) {
        m_rearLeftShooter.set(speed);
        // m_rearRightShooter.set(speed);
    }

    /** Stop the shooter */
    public void stop() {
        m_frontLeftShooter.set(0);
        // m_frontRightShooter.set(0);
        m_rearLeftShooter.set(0);
        // m_rearRightShooter.set(0);
    }

    /** Spin both shooter motors to target RPM */
    // public void setShooterRPM(double rpm) {
    //     m_frontLeftController.setReference(rpm, ControlType.kVelocity);
    //     m_frontRightController.setReference(rpm, ControlType.kVelocity);
    // }

    /** Spin shooter to default RPM */
    // public void spinShooter() {
    //     setShooterRPM(ShooterConstants.kDefaultShooterRPM);
    // }

    /** Stop both shooter motors */
    // public void stopShooter() {
    //     m_frontLeftShooter.set(0);
    //     m_frontRightShooter.set(0);
    // }

    // /** Get current left motor RPM */
    // public double getLeftRPM() {
    //     return m_frontLeftEncoder.getVelocity();
    // }

    // /** Get current right motor RPM */
    // public double getRightRPM() {
    //     return m_frontRightEncoder.getVelocity();
    // }
}
