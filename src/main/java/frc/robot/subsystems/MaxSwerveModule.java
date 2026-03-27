package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.AbsoluteEncoder;

import frc.robot.Configs;

public class MAXSwerveModule {
    private final CANSparkMax m_drivingMotor;
    private final CANSparkMax m_turningMotor;

    private final RelativeEncoder m_drivingEncoder;
    private final AbsoluteEncoder m_turningEncoder;

    private double m_chassisAngularOffset = 0.0;
    private SwerveModuleState m_desiredState = new SwerveModuleState(0.0, new Rotation2d());

    public MAXSwerveModule(int drivingCANId, int turningCANId, double chassisAngularOffset) {
        m_drivingMotor = new CANSparkMax(drivingCANId, MotorType.kBrushless);
        m_turningMotor = new CANSparkMax(turningCANId, MotorType.kBrushless);

        m_drivingEncoder = m_drivingMotor.getEncoder();
        m_turningEncoder = m_turningMotor.getAbsoluteEncoder();

        // Configure motors
        m_drivingMotor.restoreFactoryDefaults();
        m_turningMotor.restoreFactoryDefaults();

        m_drivingMotor.setIdleMode(IdleMode.kBrake);
        m_turningMotor.setIdleMode(IdleMode.kBrake);

        m_chassisAngularOffset = chassisAngularOffset;

        // Reset driving encoder
        m_drivingEncoder.setPosition(0);

        // Optional: apply config from your Configs class
        // Example: m_drivingMotor.setSmartCurrentLimit(Configs.MAXSwerveModule.currentLimit);
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(
            m_drivingEncoder.getVelocity(),
            new Rotation2d(m_turningEncoder.getPosition() - m_chassisAngularOffset)
        );
    }

    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(
            m_drivingEncoder.getPosition(),
            new Rotation2d(m_turningEncoder.getPosition() - m_chassisAngularOffset)
        );
    }

    public void setDesiredState(SwerveModuleState desiredState) {
        // Apply chassis angular offset
        SwerveModuleState correctedState = new SwerveModuleState(
            desiredState.speedMetersPerSecond,
            desiredState.angle.plus(Rotation2d.fromRadians(m_chassisAngularOffset))
        );

        // Optimize to minimize rotation
        correctedState = SwerveModuleState.optimize(correctedState, new Rotation2d(m_turningEncoder.getPosition()));

        // Set motor outputs (speed and angle)
        m_drivingMotor.set(correctedState.speedMetersPerSecond); // Could scale to voltage if needed
        m_turningMotor.set(correctedState.angle.getRadians());   // Simple position control, can add PID if needed

        m_desiredState = desiredState;
    }

    public void resetEncoders() {
        m_drivingEncoder.setPosition(0);
    }
}
