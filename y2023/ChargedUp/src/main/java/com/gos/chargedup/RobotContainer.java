// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.chargedup;


import com.gos.chargedup.autonomous.AutonomousFactory;
import com.gos.chargedup.commands.AutomatedTurretToSelectedPegCommand;
import com.gos.chargedup.commands.ChecklistTestAll;
import com.gos.chargedup.commands.CurvatureDriveCommand;
import com.gos.chargedup.commands.TeleopDockingArcadeDriveCommand;
import com.gos.chargedup.commands.testing.TestLineCommandGroup;
import com.gos.chargedup.commands.testing.TestMildCurveCommandGroup;
import com.gos.chargedup.commands.testing.TestSCurveCommandGroup;
import com.gos.chargedup.subsystems.ArmSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.gos.chargedup.subsystems.ClawSubsystem;
import com.gos.chargedup.subsystems.IntakeSubsystem;
import com.gos.chargedup.subsystems.LEDManagerSubsystem;
import com.gos.chargedup.subsystems.TurretSubsystem;
import com.pathplanner.lib.server.PathPlannerServer;
import edu.wpi.first.hal.AllianceStationID;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.simulation.DriverStationSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import java.util.function.DoubleSupplier;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...

    private final TurretSubsystem m_turret;

    private final IntakeSubsystem m_intake;
    private final ChassisSubsystem m_chassisSubsystem;

    private final ArmSubsystem m_arm;
    private final AutonomousFactory m_autonomousFactory;

    private final ClawSubsystem m_claw;

    // Replace with CommandPS4Controller or CommandJoystick if needed
    private final CommandXboxController m_driverController =
        new CommandXboxController(Constants.DRIVER_CONTROLLER_PORT);

    private final CommandXboxController m_operatorController =
        new CommandXboxController(Constants.OPERATOR_CONTROLLER_PORT);

    private final LEDManagerSubsystem m_ledManagerSubsystem = new LEDManagerSubsystem(m_driverController); //NOPMD

    private final DoubleSupplier m_pressureSupplier;

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer(DoubleSupplier pressureSupplier) {
        // Configure the trigger bindings


        m_turret = new TurretSubsystem();
        m_chassisSubsystem = new ChassisSubsystem();
        m_claw = new ClawSubsystem();
        m_arm = new ArmSubsystem();
        m_intake = new IntakeSubsystem();

        m_autonomousFactory = new AutonomousFactory(m_chassisSubsystem);

        m_pressureSupplier = pressureSupplier;
        configureBindings();

        if (RobotBase.isSimulation()) {
            DriverStationSim.setEnabled(true);
            DriverStationSim.setAllianceStationId(AllianceStationID.Blue1);
        }

        DriverStation.silenceJoystickConnectionWarning(true);
        PathPlannerServer.startServer(5811); // 5811 = port number. adjust this according to your needs

        SmartDashboard.putData("superStructure", new SuperstructureSendable());
        SmartDashboard.putData("Run checklist", new ChecklistTestAll(m_pressureSupplier, m_chassisSubsystem, m_arm, m_turret, m_intake, m_claw));
        createTestCommands();
    }

    private void createTestCommands() {
        ShuffleboardTab tab = Shuffleboard.getTab("TestCommands");

        // testing
        tab.add("Tune Chassis Velocity", m_chassisSubsystem.commandChassisVelocity());
        tab.add("Sync Odometry", m_chassisSubsystem.syncOdometryWithPoseEstimator());

        // auto trajectories
        tab.add("Test Line", new TestLineCommandGroup(m_chassisSubsystem));
        tab.add("Test Mild Curve", new TestMildCurveCommandGroup(m_chassisSubsystem));
        tab.add("Test S Curve", new TestSCurveCommandGroup(m_chassisSubsystem));

        // auto engage
        tab.add("Auto Engage", m_chassisSubsystem.createAutoEngageCommand());

        // chassis reset odometry test
        tab.add("Chassis position tune: (0, 0, 0)", m_chassisSubsystem.createResetOdometry(new Pose2d(0, 0, Rotation2d.fromDegrees(0))));
        tab.add("Chassis position tune: (0, 0, 90 deg)", m_chassisSubsystem.createResetOdometry(new Pose2d(0, 0, Rotation2d.fromDegrees(90))));
        tab.add("Chassis position tune: (0, 0, -90 deg)", m_chassisSubsystem.createResetOdometry(new Pose2d(0, 0, Rotation2d.fromDegrees(-90))));

        // turret
        tab.add("Tune Turret Velocity", m_turret.createTuneVelocity());
        tab.add("Automated Turret - 2", new AutomatedTurretToSelectedPegCommand(m_chassisSubsystem, m_turret, FieldConstants.LOW_TRANSLATIONS[2]));
        tab.add("Automated Turret - 6", new AutomatedTurretToSelectedPegCommand(m_chassisSubsystem, m_turret, FieldConstants.LOW_TRANSLATIONS[6]));
        tab.add("Automated Turret - 8", new AutomatedTurretToSelectedPegCommand(m_chassisSubsystem, m_turret, FieldConstants.LOW_TRANSLATIONS[8]));
        tab.add("Turret To Break Mode", m_turret.createTurretToBrakeMode());
        tab.add("Turret To Coast Mode", m_turret.createTurretToCoastMode());
        tab.add("Reset Turret Encoder", m_turret.createResetEncoder());
        tab.add("Move Turret Clockwise", m_turret.commandMoveTurretClockwise());
        tab.add("Move Turret Counter Clockwise", m_turret.commandMoveTurretCounterClockwise());
        tab.add("Tune Turret Position (-90 degrees)", m_turret.commandTurretPID(-90));
        tab.add("Turret PID - 0 degrees", m_turret.commandTurretPID(0));
        tab.add("Turret PID - 90 degrees", m_turret.commandTurretPID(90));
        tab.add("Turret PID - 180 degrees", m_turret.commandTurretPID(180));

        // arm pivot
        tab.add("Arm Pivot Down", m_arm.commandPivotArmDown());
        tab.add("Arm Pivot Up", m_arm.commandPivotArmUp());

        tab.add("Arm angle PID - 0 degrees", m_arm.commandPivotArmToAngle(0));
        tab.add("Arm angle PID - 45 degrees", m_arm.commandPivotArmToAngle(45));
        tab.add("Arm angle PID - 90 degrees", m_arm.commandPivotArmToAngle(90));

        tab.add("Reset Pivot Encoder", m_arm.createResetPivotEncoder(ArmSubsystem.MIN_ANGLE_DEG));
        tab.add("Reset Pivot Encoder (0 deg)", m_arm.createResetPivotEncoder(0));
        tab.add("Pivot to Coast Mode", m_arm.createPivotToCoastMode());
        tab.add("Pivot to Brake Mode", m_arm.createPivotToBrakeMode());

        // arm extension
        tab.add("Arm Full Retract", m_arm.commandFullRetract());
        tab.add("Arm Mid Retract", m_arm.commandMiddleRetract());
        tab.add("Arm Full Extend", m_arm.commandFullExtend());

        tab.add("Arm Inner Piston Extended", m_arm.commandInnerPistonExtended());
        tab.add("Arm Inner Piston Retracted", m_arm.commandInnerPistonRetracted());
        tab.add("Outer Inner Piston Extended", m_arm.commandOuterPistonExtended());
        tab.add("Outer Inner Piston Retracted", m_arm.commandOuterPistonRetracted());

        // claw
        tab.add("Claw In", m_claw.createMoveClawIntakeInCommand());
        tab.add("Claw Out", m_claw.createMoveClawIntakeOutCommand());

        // intake
        tab.add("Intake Out", m_intake.createIntakeOutCommand());
        tab.add("Intake In", m_intake.createIntakeInCommand());

        tab.add("Intake Roller In", m_intake.createIntakeInCommand());
        tab.add("Intake Roller Out", m_intake.createIntakeOutCommand());

        tab.add("Tune Gravity Offset", m_arm.tuneGravityOffsetPID());


    }

    /**
     * Use this method to define your trigger->command mappings. Triggers can be created via the
     * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
     * predicate, or via the named factories in {@link
     * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
     * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
     * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
     * joysticks}.
     */
    private void configureBindings() {
        m_chassisSubsystem.setDefaultCommand(new CurvatureDriveCommand(m_chassisSubsystem, m_driverController));

        // Driver
        m_driverController.a().whileTrue(m_arm.commandFullExtend());
        m_driverController.x().whileTrue(m_arm.commandFullRetract());
        m_driverController.y().whileTrue(m_arm.commandMiddleRetract());
        m_driverController.leftBumper().whileTrue(m_ledManagerSubsystem.commandConeGamePieceSignal());
        m_driverController.rightBumper().whileTrue(m_ledManagerSubsystem.commandCubeGamePieceSignal());
        m_driverController.leftTrigger().whileTrue(new TeleopDockingArcadeDriveCommand(m_chassisSubsystem, m_driverController));

        // Operator
        m_operatorController.y().whileTrue(m_arm.commandPivotArmUp());
        m_operatorController.a().whileTrue(m_arm.commandPivotArmDown());
        m_operatorController.leftBumper().whileTrue(m_turret.commandMoveTurretClockwise());
        m_operatorController.rightBumper().whileTrue(m_turret.commandMoveTurretCounterClockwise());
        m_operatorController.leftTrigger().onTrue(m_arm.commandInnerPistonExtended());
        m_operatorController.rightTrigger().onTrue(m_arm.commandInnerPistonRetracted());
    }


    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An example command will be run in autonomous
        return m_autonomousFactory.getAutonomousCommand();
    }


    private class SuperstructureSendable implements Sendable {

        @Override
        public void initSendable(SendableBuilder builder) {
            builder.setSmartDashboardType(SmartDashboardNames.SUPER_STRUCTURE);

            builder.addDoubleProperty(
                SmartDashboardNames.ARM_ANGLE, m_arm::getArmAngleDeg, null);
            builder.addDoubleProperty(
                SmartDashboardNames.ARM_GOAL_ANGLE, m_arm::getArmAngleGoal, null);
            builder.addBooleanProperty(
                SmartDashboardNames.ARM_EXTENSION1, m_arm::isInnerPistonIn, null);
            builder.addBooleanProperty(
                SmartDashboardNames.ARM_EXTENSION2, m_arm::isOuterPistonIn, null);
            builder.addDoubleProperty(
                SmartDashboardNames.ARM_SPEED, m_arm::getArmMotorSpeed, null);
            builder.addDoubleProperty(
                SmartDashboardNames.INTAKE_SPEED, m_intake::getHopperSpeed, null);
            builder.addBooleanProperty(
                SmartDashboardNames.INTAKE_DOWN, m_intake::isIntakeDown, null);
            builder.addDoubleProperty(
                SmartDashboardNames.TURRET_SPEED, m_turret::getTurretSpeed, null);
            builder.addDoubleProperty(
                SmartDashboardNames.TURRET_ANGLE, m_turret::getTurretAngleDeg, null);
            builder.addDoubleProperty(
                SmartDashboardNames.TURRET_GOAL_ANGLE, m_turret::getTurretAngleGoalDeg, null);
            builder.addDoubleProperty(
                SmartDashboardNames.ROBOT_ANGLE, () -> m_chassisSubsystem.getPose().getRotation().getDegrees(), null);

        }
    }
}
