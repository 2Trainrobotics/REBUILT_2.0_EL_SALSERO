package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import frc.robot.constants.FeederConstants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class FeederSubsystem extends SubsystemBase {

    private final SparkMax m_lowFeederMotor = new SparkMax(FeederConstants.kLowFeederMotorCanId, MotorType.kBrushless);
    private final SparkMax m_leftHighFeederMotor = new SparkMax(FeederConstants.kLeftHighFeederMotorCanId, MotorType.kBrushless);
    private final SparkMax m_rightHighFeederMotor = new SparkMax(FeederConstants.kRightHighFeederMotorCanId, MotorType.kBrushless);

    public FeederSubsystem() {
        // Setup motors
        SparkMaxConfig lowFeederConfig = new SparkMaxConfig();
        lowFeederConfig.idleMode(SparkBaseConfig.IdleMode.kCoast).inverted(false);
        SparkMaxConfig leftHighFeederConfig = new SparkMaxConfig();
        leftHighFeederConfig.idleMode(SparkBaseConfig.IdleMode.kCoast).inverted(false);
        SparkMaxConfig rightHighFeederConfig = new SparkMaxConfig();
        rightHighFeederConfig.idleMode(SparkBaseConfig.IdleMode.kCoast).inverted(true).follow(m_leftHighFeederMotor, true);
        m_lowFeederMotor.configure(lowFeederConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        m_leftHighFeederMotor.configure(leftHighFeederConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        m_rightHighFeederMotor.configure(rightHighFeederConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void setLowFeederSpeed(double speed) {
        m_lowFeederMotor.set(speed);
    }

    public void setHighFeederSpeed(double speed) {
        m_leftHighFeederMotor.set(speed);
    }
}
