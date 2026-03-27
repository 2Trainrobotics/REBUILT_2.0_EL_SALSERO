// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkBase.SoftLimitDirection;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
// import edu.wpi.first.wpilibj.drive.RobotDriveBase.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  // Motor Controller ID map begins here
  private CANSparkMax driveLeftLeader     = new CANSparkMax(1, MotorType.kBrushed);
  private CANSparkMax driveLeftFollower   = new CANSparkMax(2, MotorType.kBrushed);
  private CANSparkMax driveRightLeader    = new CANSparkMax(3, MotorType.kBrushed);
  private CANSparkMax driveRightFollower  = new CANSparkMax(4, MotorType.kBrushed);

  private CANSparkMax climberLeader       = new CANSparkMax(5, MotorType.kBrushless);
  private CANSparkMax climberFollower     = new CANSparkMax(6, MotorType.kBrushless);

  private CANSparkMax intakeRoller        = new CANSparkMax(7, MotorType.kBrushless);
  private CANSparkMax intakeSwivel        = new CANSparkMax(8, MotorType.kBrushless);

  private CANSparkMax shooter             = new CANSparkMax(9, MotorType.kBrushless);
  // Motor Controller ID map ends here


  // Set up the two Xbox controllers.
  // driverController is for driving.
  // operatorController is for arm and intake manipulation.
  private XboxController driverController   = new XboxController(0);
  private XboxController operatorController = new XboxController(1);


  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private static Timer m_timer;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    // Configure motors to turn correct direction.
    driveLeftLeader.setInverted(true);
    driveLeftLeader.getEncoder().setPosition(0);
    driveLeftLeader.setIdleMode(IdleMode.kBrake);
    driveLeftFollower.setInverted(true);
    driveLeftFollower.getEncoder().setPosition(0);
    driveLeftFollower.setIdleMode(IdleMode.kBrake);
    driveLeftFollower.follow(driveLeftLeader);

    driveRightLeader.setInverted(false);
    driveRightLeader.getEncoder().setPosition(0);
    driveRightLeader.setIdleMode(IdleMode.kBrake);
    driveRightFollower.setInverted(false);
    driveRightFollower.getEncoder().setPosition(0);
    driveRightFollower.setIdleMode(IdleMode.kBrake);
    driveRightFollower.follow(driveRightLeader);
    
    climberLeader.getEncoder().setPosition(0);
    climberLeader.setIdleMode(IdleMode.kBrake);
    climberFollower.setIdleMode(IdleMode.kBrake);
    climberFollower.follow(climberLeader);
    climberLeader.setSoftLimit(SoftLimitDirection.kForward , 2);
    climberLeader.setSoftLimit(SoftLimitDirection.kReverse, 0);
    climberLeader.enableSoftLimit(SoftLimitDirection.kForward, true);
    climberLeader.enableSoftLimit(SoftLimitDirection.kReverse, true);
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
    m_timer.reset();
    m_timer.start();

  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // if (m_autoSelected == kDefaultAuto){
      if (!m_timer.hasElapsed(2)) {
        driveLeftLeader.set(1);
        driveRightLeader.set(1);
      } else {
        driveLeftLeader.set(0);
        driveRightLeader.set(0);
      }
    // } else {
    //   if (!m_timer.hasElapsed(5)) {
    //     driveLeftLeader.set(1);
    //     driveRightLeader.set(1);
    //   } else {
    //     driveLeftLeader.set(0);
    //     driveRightLeader.set(0);

    //     if (!m_timer.hasElapsed(7)){
    //       shooter.set(1);
    //     } else {
    //       shooter.set(0);
    //     }
    //   }
    // }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

    ////////////////////////////////////////////////////
    //   DRIVETRAIN
    ////////////////////////////////////////////////////

    double driveLeftPower = driverController.getLeftY();
    double driveRightPower = driverController.getRightY();

    // double leftPowerPositive = (driveLeftPower >= 0) ? 1.0 : -1.0;
    // double rightPowerPositive = (driveRightPower >= 0) ? 1.0 : -1.0;
    // double topSpeedFactor = 0.25;
    // if (driverController.getLeftBumper()) {
    //   topSpeedFactor = topSpeedFactor * 3 / 4;
    // }
    // if (driverController.getRightBumper()) {
    //   topSpeedFactor = topSpeedFactor * 3 / 4;
    // }

    // driveLeftPower = driveLeftPower * driveLeftPower;
    // driveLeftPower *= leftPowerPositive;
    // driveLeftPower *= topSpeedFactor;
    // driveRightPower = driveRightPower * driveRightPower;
    // driveRightPower *= rightPowerPositive;
    // driveRightPower *= topSpeedFactor;

    // SmartDashboard.putNumber("driveLeftPower (pre-safety)", driveLeftPower);
    // SmartDashboard.putNumber("driveRightPower (pre-safety)", driveRightPower);

    if (Math.abs(driveLeftPower) < 0.1) {
      driveLeftPower = 0;
    }
    if (Math.abs(driveRightPower) < 0.1) {
      driveRightPower = 0;
    }

    SmartDashboard.putNumber("driveLeftPower", driveLeftPower);
    SmartDashboard.putNumber("driveRightPower", driveRightPower);

    driveLeftLeader.set(driveLeftPower);
    // driveLeftFollower.set(driveLeftPower);
    driveRightLeader.set(driveRightPower);
    // driveRightFollower.set(driveRightPower);


    ////////////////////////////////////////////////////
    //   CLIMBER
    ////////////////////////////////////////////////////

    // boolean climbDown = driverController.getLeftBumper();
    // boolean climbUp = driverController.getRightBumper();

    // if (climbDown) {
    //   climberLeader.set(-0.5);
    // } else if (climbUp) {
    //   climberLeader.set(0.5);
    // }

    ////////////////////////////////////////////////////
    //   INTAKE
    ////////////////////////////////////////////////////

    double intakeRollerPower = operatorController.getLeftY();
    double intakeSwivelPower = operatorController.getRightY();

    if (Math.abs(intakeRollerPower) < 0.1) {
      intakeRollerPower = 0;
    }
    if (Math.abs(intakeSwivelPower) < 0.1) {
      intakeSwivelPower = 0;
    }

    intakeRoller.set(intakeRollerPower);
    intakeSwivel.set(intakeSwivelPower);

    ////////////////////////////////////////////////////
    //   SHOOTER
    ////////////////////////////////////////////////////

    boolean enableShooter = operatorController.getLeftBumper() || operatorController.getRightBumper();

    if (enableShooter) {
      shooter.set(1.0);
    } else {
      shooter.set(0.0);
    }

  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}

