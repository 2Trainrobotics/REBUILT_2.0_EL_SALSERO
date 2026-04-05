package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.constants.OIConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.FeederSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class Robot extends TimedRobot {
    /* CAN ID Map
     * Drive Motors:
     *   Front Left:   23 (drive), 14 (turning)
     *   Front Right:  22 (drive), 16 (turning)
     *   Rear Left:    13 (drive), 25 (turning)
     *   Rear Right:    8 (drive), 20 (turning)
     * 
     * Shooter Motors:
     *   Front Left:   15 (shooter)
     *   Front Right:   6 (shooter)
     *   Rear Left:    10 (shooter) SparkFlex
     *   Rear Right:    9 (shooter) SparkFlex
     * 
     * Intake Motors:
     *   Left Swing:    4 (swing)
     *   Right Swing:  11 (swing)
     *   Intake:       12 (intake)
     * 
     * Feeder Motors:
     *   Feeder Low:        33 (feeder)
     *   Feeder High Left:  21 (feeder)
     *   Feeder High Right:  7 (feeder)
     */
    private final DriveSubsystem robotDrive = new DriveSubsystem();
    private final IntakeSubsystem robotIntake = new IntakeSubsystem();
    private final ShooterSubsystem robotShooter = new ShooterSubsystem();
    private final FeederSubsystem robotFeeder = new FeederSubsystem();

    // Controllers
    private XboxController driverController = new XboxController(0);
    private XboxController operatorController = new XboxController(1);

    private static final String kDefaultAuto = "Default";
    private static final String kCustomAuto = "My Auto";
    private String m_autoSelected;
    private static Timer m_timer = new Timer();
    private final SendableChooser<String> m_chooser = new SendableChooser<>();

    @Override
    public void robotInit() {
        // Auto chooser
        m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
        m_chooser.addOption("My Auto", kCustomAuto);
        SmartDashboard.putData("Auto choices", m_chooser);
    }

    @Override
    public void robotPeriodic() {}

    @Override
    public void autonomousInit() {
        // m_autoSelected = m_chooser.getSelected();
        // System.out.println("Auto selected: " + m_autoSelected);
        // m_timer.reset();
        // m_timer.start();
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {}

    @Override
    public void teleopPeriodic() {
        // 1. Drive:  driver controller, left and right joysticks
        robotDrive.drive(
            -MathUtil.applyDeadband(driverController.getLeftY(), OIConstants.kDriveDeadband),
            -MathUtil.applyDeadband(driverController.getLeftX(), OIConstants.kDriveDeadband),
            -MathUtil.applyDeadband(driverController.getRightX(), OIConstants.kDriveDeadband),
            true);

        if (driverController.getXButton()) {
            robotShooter.setFrontShooterSpeed(0.1);
            robotShooter.setRearShooterSpeed(-0.3);
        } else {
            robotShooter.setFrontShooterSpeed(0);
            robotShooter.setRearShooterSpeed(0);
        }

        if (driverController.getYButton()) {
            robotFeeder.setHighFeederSpeed(0.5);
        } else {
            robotFeeder.setHighFeederSpeed(0);
        }

        /////////// Modules below not working; wiring issue?

        /////////// Control Scheme 1

        // if (driverController.getXButton()) {
        //     robotIntake.setIntakeSpeed(0.5);
        // } else {
        //     robotIntake.setIntakeSpeed(0);
        // }

        // if (driverController.getAButton()) {
        //     robotFeeder.setLowFeederSpeed(0.5);
        // } else if (driverController.getBButton()) {
        //     robotFeeder.setLowFeederSpeed(-0.5);
        // } else {
        //     robotFeeder.setLowFeederSpeed(0);
        // }

        // if (driverController.getStartButton()) {
        //     robotIntake.setSwingSpeed(0.5);
        // } else if (driverController.getBackButton()) {
        //     robotIntake.setSwingSpeed(-0.5);
        // } else {
        //     robotIntake.setSwingSpeed(0);
        // }

        /////////// Control Scheme 2

        if (driverController.getStartButton()) {
            robotIntake.setIntakeSpeed(0.5);
        } else if (driverController.getBackButton()) {
            robotIntake.setIntakeSpeed(-0.5);
        } else {
            robotIntake.setIntakeSpeed(0);
        }

        if (driverController.getLeftTriggerAxis() > 0.2) {
            robotFeeder.setLowFeederSpeed(0.5);
        } else if (driverController.getRightTriggerAxis() > 0.2) {
            robotFeeder.setLowFeederSpeed(-0.5);
        } else {
            robotFeeder.setLowFeederSpeed(0);
        }

        if (driverController.getPOV() >= 315 || driverController.getPOV() <= 45) {
            robotIntake.setSwingSpeed(0.5);
        } if (driverController.getPOV() >= 135 || driverController.getPOV() <= 225) {
            robotIntake.setSwingSpeed(-0.5);
        } else {
            robotIntake.setSwingSpeed(0);
        }


        // SmartDashboard.putNumber("shooterLeftRPM", shooterLeftEncoder.getVelocity());
        // SmartDashboard.putNumber("shooterRightRPM", shooterRightEncoder.getVelocity());
    }

    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {}

    @Override
    public void testInit() {}

    @Override
    public void testPeriodic() {}

    @Override
    public void simulationInit() {}

    @Override
    public void simulationPeriodic() {}
}
