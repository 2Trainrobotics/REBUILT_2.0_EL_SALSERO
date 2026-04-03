package frc.robot;


import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.constants.OIConstants;
// import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.FeederSubsystem;
// import frc.robot.subsystems.IntakeSubsystem;
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
     *   Left Swing:   ? (swing)
     *   Right Swing:  ? (swing)
     *   Intake:       ? (intake)
     * 
     * Feeder Motors:
     *   Feeder Low:   ? (feeder)
     *   Feeder High Left:  21 (feeder)
     *   Feeder High Right:  7 (feeder)
     */
    // private final DriveSubsystem robotDrive = new DriveSubsystem();
    // private final IntakeSubsystem robotIntake = new IntakeSubsystem();
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
        // robotDrive.drive(
        //     -MathUtil.applyDeadband(driverController.getLeftY(), OIConstants.kDriveDeadband),
        //     -MathUtil.applyDeadband(driverController.getLeftX(), OIConstants.kDriveDeadband),
        //     -MathUtil.applyDeadband(driverController.getRightX(), OIConstants.kDriveDeadband),
        //     true);

        if (driverController.getXButton()) {
            robotShooter.setFrontShooterSpeed(0.5);
        } else if (driverController.getYButton()) {
            robotShooter.setFrontShooterSpeed(-0.5);
        } else {
            robotShooter.setFrontShooterSpeed(0);
        }

        if (driverController.getAButton()) {
            robotShooter.setRearShooterSpeed(0.5);
        } else if (driverController.getBButton()) {
            robotShooter.setRearShooterSpeed(-0.5);
        } else {
            robotShooter.setRearShooterSpeed(0);
        }

        if (driverController.getLeftBumperButton()) {
            robotFeeder.setHighFeederSpeed(0.5);
        } else if (driverController.getRightBumperButton()) {
            robotFeeder.setHighFeederSpeed(-0.5);
        } else {
            robotFeeder.setHighFeederSpeed(0);
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
