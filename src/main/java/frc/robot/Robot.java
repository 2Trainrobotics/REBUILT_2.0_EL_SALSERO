// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkBase.SoftLimitDirection;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.ControlType;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.constants.ShooterConstants;

public class Robot extends TimedRobot {

    // Drive Motors (still CANSparkMax)
    private CANSparkMax driveLeftLeader = new CANSparkMax(1, MotorType.kBrushed);
    private CANSparkMax driveLeftFollower = new CANSparkMax(2, MotorType.kBrushed);
    private CANSparkMax driveRightLeader = new CANSparkMax(3, MotorType.kBrushed);
    private CANSparkMax driveRightFollower = new CANSparkMax(4, MotorType.kBrushed);

    // Climber
    private CANSparkMax climberLeader = new CANSparkMax(5, MotorType.kBrushless);
    private CANSparkMax climberFollower = new CANSparkMax(6, MotorType.kBrushless);

    // Intake
    private CANSparkMax intakeRoller = new CANSparkMax(7, MotorType.kBrushless);
    private CANSparkMax intakeSwivel = new CANSparkMax(8, MotorType.kBrushless);

    // Shooter (Spark Flex)
    private SparkFlex shooterLeft = new SparkFlex(ShooterConstants.kLeftShooterCanId);
    private SparkFlex shooterRight = new SparkFlex(ShooterConstants.kRightShooterCanId);

    private RelativeEncoder shooterLeftEncoder;
    private RelativeEncoder shooterRightEncoder;

    private SparkClosedLoopController shooterLeftController;
    private SparkClosedLoopController shooterRightController;

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

        // Drive motors configuration
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

        // Climber configuration
        climberLeader.getEncoder().setPosition(0);
        climberLeader.setIdleMode(IdleMode.kBrake);
        climberFollower.setIdleMode(IdleMode.kBrake);
        climberFollower.follow(climberLeader);
        climberLeader.setSoftLimit(SoftLimitDirection.kForward, 2);
        climberLeader.setSoftLimit(SoftLimitDirection.kReverse, 0);
        climberLeader.enableSoftLimit(SoftLimitDirection.kForward, true);
        climberLeader.enableSoftLimit(SoftLimitDirection.kReverse, true);

        // Shooter setup
        shooterLeftEncoder = shooterLeft.getEncoder();
        shooterRightEncoder = shooterRight.getEncoder();

        shooterLeftController = shooterLeft.getClosedLoopController();
        shooterRightController = shooterRight.getClosedLoopController();

        shooterLeftController.setPID(ShooterConstants.kP, ShooterConstants.kI, ShooterConstants.kD);
        shooterRightController.setPID(ShooterConstants.kP, ShooterConstants.kI, ShooterConstants.kD);

        shooterLeftController.setFF(ShooterConstants.kFF);
        shooterRightController.setFF(ShooterConstants.kFF);

        shooterLeftEncoder.setPosition(0);
        shooterRightEncoder.setPosition(0);
    }

    @Override
    public void robotPeriodic() {}

    @Override
    public void autonomousInit() {
        m_autoSelected = m_chooser.getSelected();
        System.out.println("Auto selected: " + m_autoSelected);
        m_timer.reset();
        m_timer.start();
    }

    @Override
    public void autonomousPeriodic() {
        if (!m_timer.hasElapsed(2)) {
            driveLeftLeader.set(1);
            driveRightLeader.set(1);
        } else {
            driveLeftLeader.set(0);
            driveRightLeader.set(0);
        }
    }

    @Override
    public void teleopInit() {}

    @Override
    public void teleopPeriodic() {

        /////////////////
        // DRIVETRAIN
        /////////////////
        double driveLeftPower = driverController.getLeftY();
        double driveRightPower = driverController.getRightY();

        if (Math.abs(driveLeftPower) < 0.1) driveLeftPower = 0;
        if (Math.abs(driveRightPower) < 0.1) driveRightPower = 0;

        SmartDashboard.putNumber("driveLeftPower", driveLeftPower);
        SmartDashboard.putNumber("driveRightPower", driveRightPower);

        driveLeftLeader.set(driveLeftPower);
        driveRightLeader.set(driveRightPower);

        /////////////////
        // CLIMBER
        /////////////////
        // Example: uncomment and use buttons if needed
        // if (driverController.getLeftBumper()) climberLeader.set(-0.5);
        // if (driverController.getRightBumper()) climberLeader.set(0.5);

        /////////////////
        // INTAKE
        /////////////////
        double intakeRollerPower = operatorController.getLeftY();
        double intakeSwivelPower = operatorController.getRightY();

        if (Math.abs(intakeRollerPower) < 0.1) intakeRollerPower = 0;
        if (Math.abs(intakeSwivelPower) < 0.1) intakeSwivelPower = 0;

        intakeRoller.set(intakeRollerPower);
        intakeSwivel.set(intakeSwivelPower);

        /////////////////
        // SHOOTER
        /////////////////
        boolean enableShooter = operatorController.getLeftBumper() || operatorController.getRightBumper();
        if (enableShooter) {
            shooterLeftController.setSetpoint(ShooterConstants.kDefaultShooterRPM, ControlType.kVelocity);
            shooterRightController.setSetpoint(ShooterConstants.kDefaultShooterRPM, ControlType.kVelocity);
        } else {
            shooterLeft.set(0);
            shooterRight.set(0);
        }

        SmartDashboard.putNumber("shooterLeftRPM", shooterLeftEncoder.getVelocity());
        SmartDashboard.putNumber("shooterRightRPM", shooterRightEncoder.getVelocity());
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
