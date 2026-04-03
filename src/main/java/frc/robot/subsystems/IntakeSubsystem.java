package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.IntakeConstants;

public class IntakeSubsystem extends SubsystemBase {

    public final SparkMax m_leftSwingMotor = new SparkMax(IntakeConstants.kLeftSwingMotorCanId, MotorType.kBrushless);
    public final SparkMax m_rightSwingMotor = new SparkMax(IntakeConstants.kRightSwingMotorCanId, MotorType.kBrushless);

    public final SparkMax m_intakeMotor = new SparkMax(IntakeConstants.kIntakeMotorCanId, MotorType.kBrushless);

    private final RelativeEncoder m_leftSwingEncoder;

    public final double SWING_IN_LIMIT = 0;  // TODO: update with actual limit
    public final double SWING_OUT_LIMIT = 90;  // TODO: update with actual limit

    public IntakeSubsystem() {
        // Setup motors
        SparkMaxConfig leftSwingConfig = new SparkMaxConfig();
        leftSwingConfig.idleMode(SparkBaseConfig.IdleMode.kCoast).inverted(false);
        SparkMaxConfig rightSwingConfig = new SparkMaxConfig();
        rightSwingConfig.idleMode(SparkBaseConfig.IdleMode.kCoast).inverted(true).follow(m_leftSwingMotor, true);
        m_leftSwingMotor.configure(leftSwingConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        m_rightSwingMotor.configure(rightSwingConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        SparkMaxConfig intakeConfig = new SparkMaxConfig();
        intakeConfig.idleMode(SparkBaseConfig.IdleMode.kCoast).inverted(false);
        m_intakeMotor.configure(intakeConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        m_leftSwingEncoder = m_leftSwingMotor.getEncoder();
        m_leftSwingEncoder.setPosition(0);
    }

    /* TODO:
     * 
     * 1. Determine whether a positive or negative leftSwingMotor speed value spins in or out
     * 2. Assuming intake always starts in the stowed position, determine the encoder position value for the intake fully out
     * 3. Implement limit guards for swingIntakeIn and swingIntakeOut
     * 4. Determine whether a positive or negative intakeMotor speed value intakes or outtakes
     */

    public void swingIntakeOut() {
        // TODO:  determine how far is too far for the intake to swing out and implement limit switch or encoder feedback to prevent damage
        // if (m_leftSwingEncoder.getPosition() < SWING_OUT_LIMIT) {
        m_leftSwingMotor.set(0.5);
    }

    public void swingIntakeIn() {
        // TODO:  determine how far is too far for the intake to swing in and implement limit switch or encoder feedback to prevent damage
        // if (m_leftSwingEncoder.getPosition() > SWING_IN_LIMIT) {
        m_leftSwingMotor.set(-0.5);
    }

    public void stopSwing() {
        m_leftSwingMotor.set(0);
    }

    /** Set intake speed from -1.0 to 1.0 */
    public void setIntakeSpeed(double speed) {
        // m_intakeMotor.set(speed);
    }

    /** Stop the intake motor */
    public void stopIntake() {
        // m_intakeMotor.set(0);
    }
    
}
