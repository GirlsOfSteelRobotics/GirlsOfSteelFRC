// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.chargedup;


import com.gos.chargedup.autonomous.AutonomousFactory;
import com.gos.chargedup.commands.AutomatedTurretToSelectedPegCommand;
import com.gos.chargedup.commands.ChecklistTestAll;
import com.gos.chargedup.commands.CurvatureDriveCommand;
import com.gos.chargedup.subsystems.ArmSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystem;

//test paths
import com.gos.chargedup.commands.testing.TestLineCommandGroup;
import com.gos.chargedup.commands.testing.TestMildCurveCommandGroup;
import com.gos.chargedup.commands.testing.TestSCurveCommandGroup;

import com.gos.chargedup.subsystems.ClawSubsystem;
import com.gos.chargedup.subsystems.TurretSubsystem;
import com.pathplanner.lib.server.PathPlannerServer;
import edu.wpi.first.hal.AllianceStationID;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.RobotBase;
import com.gos.chargedup.subsystems.LEDManagerSubsystem;

import com.gos.chargedup.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.simulation.DriverStationSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;


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


    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer(PneumaticHub pneumaticHub) {
        // Configure the trigger bindings


        m_turret = new TurretSubsystem();
        m_chassisSubsystem = new ChassisSubsystem();
        m_claw = new ClawSubsystem();
        m_arm = new ArmSubsystem();
        m_intake = new IntakeSubsystem();

        m_autonomousFactory = new AutonomousFactory(m_chassisSubsystem);
        configureBindings();

        if (RobotBase.isSimulation()) {
            DriverStationSim.setEnabled(true);
            DriverStationSim.setAllianceStationId(AllianceStationID.Blue1);
        }
        PathPlannerServer.startServer(5811); // 5811 = port number. adjust this according to your needs

        SmartDashboard.putData("superStructure", new SuperstructureSendable());
        SmartDashboard.putData("Run checklist", new ChecklistTestAll(pneumaticHub, m_chassisSubsystem, m_arm, m_turret, m_intake, m_claw));
        createTestCommands();
    }

    private void createTestCommands() {
        ShuffleboardTab tab = Shuffleboard.getTab("TestCommands");

        SmartDashboard.putData(m_chassisSubsystem.commandChassisVelocity());
        tab.add("Chassis velocity tune", m_chassisSubsystem.commandChassisVelocity());

        tab.add("Automated Turret - 2", new AutomatedTurretToSelectedPegCommand(m_chassisSubsystem, m_turret, FieldConstants.LOW_TRANSLATIONS[2]));
        tab.add("Automated Turret - 6", new AutomatedTurretToSelectedPegCommand(m_chassisSubsystem, m_turret, FieldConstants.LOW_TRANSLATIONS[6]));
        tab.add("Automated Turret - 8", new AutomatedTurretToSelectedPegCommand(m_chassisSubsystem, m_turret, FieldConstants.LOW_TRANSLATIONS[8]));

        tab.add("Test Line", new TestLineCommandGroup(m_chassisSubsystem));
        tab.add("Test Mild Curve", new TestMildCurveCommandGroup(m_chassisSubsystem));
        tab.add("Test S Curve", new TestSCurveCommandGroup(m_chassisSubsystem));

        tab.add("Auto Engage", m_chassisSubsystem.createAutoEngageCommand());
        tab.add("Tune Turret Velocity", m_turret.createTuneVelocity());
        tab.add("Toggle Break Mode", m_turret.createToggleBrakeMode());
        tab.add("Reset Turret Encoder", m_turret.createResetEncoder());
        tab.add("Tune Turret Position (0 degrees)", m_turret.createTunePosition(0));
        tab.add("Tune Turret Position (90 degrees)", m_turret.createTunePosition(90));
        tab.add("Tune Turret Position (-90 degrees)", m_turret.createTunePosition(-90));
        tab.add("Chassis position tune: 0 deg", m_chassisSubsystem.createResetOdometry(new Pose2d(0, 0, Rotation2d.fromDegrees(0))));
        tab.add("Chassis position tune: 90 deg", m_chassisSubsystem.createResetOdometry(new Pose2d(0, 0, Rotation2d.fromDegrees(90))));
        tab.add("Chassis position tune: -90 deg", m_chassisSubsystem.createResetOdometry(new Pose2d(0, 0, Rotation2d.fromDegrees(-90))));



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
        m_driverController.a().whileTrue(m_arm.commandOut());
        m_driverController.x().whileTrue(m_arm.commandFullRetract());
        m_driverController.y().whileTrue(m_arm.commandMiddleRetract());
        m_driverController.leftBumper().whileTrue(m_turret.commandMoveTurretClockwise());
        m_driverController.rightBumper().whileTrue(m_turret.commandMoveTurretCounterClockwise());

        // Operator
        m_operatorController.leftBumper().whileTrue(m_intake.createRetractSolenoidCommand());
        m_operatorController.rightBumper().whileTrue(m_intake.createExtendSolenoidCommand());
        m_operatorController.a().whileTrue(m_arm.commandPivotArmUp());
        m_operatorController.b().whileTrue(m_arm.commandPivotArmDown());
        m_operatorController.x().whileTrue(m_claw.createMoveClawIntakeInCommand());
        m_operatorController.y().whileTrue(m_claw.createMoveClawIntakeOutCommand());
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
