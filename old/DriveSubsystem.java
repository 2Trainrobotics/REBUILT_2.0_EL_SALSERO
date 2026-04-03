package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.Pigeon2;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.DriveConstants;

public class DriveSubsystem extends SubsystemBase {

    // Spark Flex Swerve modules
    private final MAXSwerveModule frontLeft = new MAXSwerveModule(
            DriveConstants.kFrontLeftDriveMotorPort,
            DriveConstants.kFrontLeftTurnMotorPort,
            DriveConstants.kFrontLeftAngleOffset);

    private final MAXSwerveModule frontRight = new MAXSwerveModule(
            DriveConstants.kFrontRightDriveMotorPort,
            DriveConstants.kFrontRightTurnMotorPort,
            DriveConstants.kFrontRightAngleOffset);

    private final MAXSwerveModule backLeft = new MAXSwerveModule(
            DriveConstants.kBackLeftDriveMotorPort,
            DriveConstants.kBackLeftTurnMotorPort,
            DriveConstants.kBackLeftAngleOffset);

    private final MAXSwerveModule backRight = new MAXSwerveModule(
            DriveConstants.kBackRightDriveMotorPort,
            DriveConstants.kBackRightTurnMotorPort,
            DriveConstants.kBackRightAngleOffset);

    private final MAXSwerveModule[] allModules = { frontLeft, frontRight, backLeft, backRight };

    // Gyro
    private final Pigeon2 pigeon = new Pigeon2(DriveConstants.kPigeonID);

    // Odometry
    private final SwerveDriveOdometry odometry = new SwerveDriveOdometry(
            DriveConstants.kDriveKinematics,
            Rotation2d.fromDegrees(pigeon.getYaw()),
            getModulePositions());

    public DriveSubsystem() {
        pigeon.configFactoryDefault();
        zeroGyro();
    }

    /** Zero gyro yaw */
    public void zeroGyro() {
        pigeon.setYaw(0.0);
    }

    /** Returns current heading as Rotation2d */
    public Rotation2d getHeading() {
        return Rotation2d.fromDegrees(pigeon.getYaw());
    }

    /** Returns current robot pose for odometry */
    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    /** Reset odometry to a specific pose */
    public void resetOdometry(Pose2d pose) {
        odometry.resetPosition(pose, getHeading());
        for (MAXSwerveModule module : allModules) {
            module.resetEncoders();
        }
    }

    /** Returns an array of current swerve module positions */
    public SwerveModulePosition[] getModulePositions() {
        SwerveModulePosition[] positions = new SwerveModulePosition[allModules.length];
        for (int i = 0; i < allModules.length; i++) {
            positions[i] = allModules[i].getPosition();
        }
        return positions;
    }

    /** Sets desired states for all modules */
    public void setModuleStates(SwerveModuleState[] desiredStates) {
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, DriveConstants.kMaxSpeedMetersPerSecond);
        for (int i = 0; i < allModules.length; i++) {
            allModules[i].setDesiredState(desiredStates[i]);
        }
    }

    @Override
    public void periodic() {
        // Update odometry
        odometry.update(getHeading(), getModulePositions());

        // Debug to SmartDashboard
        for (int i = 0; i < allModules.length; i++) {
            SmartDashboard.putNumber("Module " + i + " Speed", allModules[i].getState().speedMetersPerSecond);
            SmartDashboard.putNumber("Module " + i + " Angle", allModules[i].getState().angle.getDegrees());
        }
        SmartDashboard.putNumber("Gyro Yaw", pigeon.getYaw());
    }
}
