package frc.robot;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkBase.SoftLimitDirection;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.constants.ShooterConstants;
import frc.robot.subsystems.ShooterSubsystem;

public class Robot extends TimedRobot {

    // Drive motors
    private final CANSparkMax driveLeftLeader  = new CANSparkMax(1, MotorType.kBrushed);
    private final CANSparkMax driveLeftFollower = new CANSparkMax(2, MotorType.kBrushed);
    private final CANSparkMax driveRightLeader = new CANSparkMax(3, MotorType.kBrushed);
    private final CANSparkMax driveRightFollower = new CANSparkMax(4, MotorType.kBrushed);

    // Climber motors
    private final CANSparkMax climberLeader = new CANSparkMax(5, MotorType.kBrushless);
    private final CANSparkMax climberFollower = new CANSparkMax(6, MotorType.kBrushless);

    // Intake motors
    private final CANSparkMax intakeRoller = new CANSparkMax(7, MotorType.kBrushless);
    private final CANSparkMax intakeSwivel = new CANSparkMax(8, MotorType.kBrushless);

    // Subsystems
    private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();

    // Controllers
    private final XboxController driverController = new XboxController(0);
    private final XboxController operatorController = new XboxController(1);

    // Autonomous chooser
    private final SendableChooser<String> autoChooser = new SendableChooser<>();
    private String autoSelected;
    private final Timer autoTimer = new Timer();

    @Override
    public void robotInit() {
        // Autonomous chooser
        autoChooser.setDefaultOption("Default Auto", "Default");
        autoChooser.addOption("Custom Auto", "Custom");
        SmartDashboard.putData("Auto Choices", autoChooser);

        // Drive setup
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

        // Climber setup
        climberLeader.getEncoder().setPosition(0);
        climberLeader.setIdleMode(IdleMode.kBrake);
        climberFollower.setIdleMode(IdleMode.kBrake);
        climberFollower.follow(climberLeader);
        climberLeader.setSoftLimit(SoftLimitDirection.kForward, 2);
        climberLeader.setSoftLimit(SoftLimitDirection.kReverse, 0);
        climberLeader.enableSoftLimit(SoftLimitDirection.kForward, true);
        climberLeader.enableSoftLimit(SoftLimitDirection.kReverse, true);
    }

    @Override
    public void autonomousInit() {
        autoSelected = autoChooser.getSelected();
        System.out.println("Auto selected: " + autoSelected);
        autoTimer.reset();
        autoTimer.start();
    }

    @Override
    public void autonomousPeriodic() {
        // Simple auto: drive forward 2 seconds
        if (!autoTimer.hasElapsed(2)) {
            driveLeftLeader.set(0.5);
            driveRightLeader.set(0.5);
        } else {
            driveLeftLeader.set(0);
            driveRightLeader.set(0);
        }
    }

    @Override
    public void teleopPeriodic() {
        driveControl();
        intakeControl();
        shooterControl();
        climberControl();
    }

    private void driveControl() {
        double leftPower = applyDeadband(driverController.getLeftY());
        double rightPower = applyDeadband(driverController.getRightY());

        driveLeftLeader.set(leftPower);
        driveRightLeader.set(rightPower);

        SmartDashboard.putNumber("Drive Left Power", leftPower);
        SmartDashboard.putNumber("Drive Right Power", rightPower);
    }

    private void intakeControl() {
        double rollerPower = applyDeadband(operatorController.getLeftY());
        double swivelPower = applyDeadband(operatorController.getRightY());

        intakeRoller.set(rollerPower);
        intakeSwivel.set(swivelPower);
    }

    private void shooterControl() {
        boolean enableShooter = operatorController.getLeftBumper() || operatorController.getRightBumper();
        if (enableShooter) {
            shooterSubsystem.setShooter(1.0);
        } else {
            shooterSubsystem.stop();
        }
    }

    private void climberControl() {
        boolean climbUp = driverController.getRightBumper();
        boolean climbDown = driverController.getLeftBumper();

        if (climbUp) {
            climberLeader.set(0.5);
        } else if (climbDown) {
            climberLeader.set(-0.5);
        } else {
            climberLeader.set(0);
        }
    }

    private double applyDeadband(double value) {
        return Math.abs(value) < 0.1 ? 0 : value;
    }
}
