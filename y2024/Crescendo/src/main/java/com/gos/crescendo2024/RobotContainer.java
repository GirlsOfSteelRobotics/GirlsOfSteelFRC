// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.crescendo2024;

import com.gos.crescendo2024.auton.Autos;
import com.gos.crescendo2024.commands.ArmPivotJoystickCommand;
import com.gos.crescendo2024.commands.DavidDriveSwerve;
import com.gos.crescendo2024.commands.TurnToPointSwerveDrive;
import com.gos.crescendo2024.subsystems.ArmPivotSubsystem;
import com.gos.crescendo2024.subsystems.ChassisSubsystem;
import com.gos.crescendo2024.subsystems.IntakeSubsystem;
import com.gos.crescendo2024.subsystems.ShooterSubsystem;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.RobotBase;
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
    // Subsystems
    private final ChassisSubsystem m_chassisSubsystem;
    private final ArmPivotSubsystem m_armPivotSubsystem;
    private final ShooterSubsystem m_shooterSubsystem;
    private final IntakeSubsystem m_intakeSubsystem;

    // Joysticks
    private final CommandXboxController m_driverController =
        new CommandXboxController(Constants.DRIVER_JOYSTICK);

    private final CommandXboxController m_operatorController =
        new CommandXboxController(Constants.OPERATOR_JOYSTICK);

    private final Autos m_autonomousFactory;


    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        m_chassisSubsystem = new ChassisSubsystem();
        m_shooterSubsystem = new ShooterSubsystem();
        m_armPivotSubsystem = new ArmPivotSubsystem();
        m_intakeSubsystem = new IntakeSubsystem();

        NamedCommands.registerCommand("shoot", m_intakeSubsystem.createMoveIntakeInCommand().withTimeout(1));

        m_autonomousFactory = new Autos();

        // Configure the trigger bindings
        configureBindings();

        createTestCommands();

        SmartDashboard.putData("super structure", new SuperstructureSendable());

        if (RobotBase.isSimulation()) {
            DriverStationSim.setEnabled(true);
        }
    }

    private void createTestCommands() {
        ShuffleboardTab shuffleboardTab = Shuffleboard.getTab("test commands");
        shuffleboardTab.add("shooterTuning", m_shooterSubsystem.createTunePercentShootCommand());
        shuffleboardTab.add("testShooter50", m_shooterSubsystem.createSetRPMCommand(50));
        shuffleboardTab.add("testShooter100", m_shooterSubsystem.createSetRPMCommand(100));
        shuffleboardTab.add("resetShooter", m_shooterSubsystem.createStopShooterCommand());

        shuffleboardTab.add("intake in", m_intakeSubsystem.createMoveIntakeInCommand());
        shuffleboardTab.add("intake out", m_intakeSubsystem.createMoveIntakeOutCommand());
        shuffleboardTab.add("Chassis to 45", m_chassisSubsystem.createTurnToAngleCommand(45));
        shuffleboardTab.add("Chassis to 90", m_chassisSubsystem.createTurnToAngleCommand(90));
        shuffleboardTab.add("Chassis to -180", m_chassisSubsystem.createTurnToAngleCommand(-180));
        shuffleboardTab.add("Chassis to -45", m_chassisSubsystem.createTurnToAngleCommand(-45));

        shuffleboardTab.add("test drive to speaker", m_chassisSubsystem.testDriveToPoint(m_chassisSubsystem, FieldConstants.Speaker.CENTER_SPEAKER_OPENING));
        shuffleboardTab.add("test drive to amp", m_chassisSubsystem.testDriveToPoint(m_chassisSubsystem, new Pose2d(FieldConstants.AMP_CENTER, Rotation2d.fromDegrees(90))));

        //hard coded sorry will fix asap
        shuffleboardTab.add("David Drive", new DavidDriveSwerve(m_chassisSubsystem, m_driverController));
        shuffleboardTab.add("Turn to point drive", new TurnToPointSwerveDrive(m_chassisSubsystem, m_driverController, FieldConstants.Speaker.CENTER_SPEAKER_OPENING));

        //arm
        shuffleboardTab.add("arm to 45", m_armPivotSubsystem.createMoveArmToAngle(45));
        shuffleboardTab.add("arm to -45", m_armPivotSubsystem.createMoveArmToAngle(-45));
        shuffleboardTab.add("arm to 90", m_armPivotSubsystem.createMoveArmToAngle(90));
        shuffleboardTab.add("arm to 0", m_armPivotSubsystem.createMoveArmToAngle(0));

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
        m_chassisSubsystem.setDefaultCommand(new DavidDriveSwerve(m_chassisSubsystem, m_driverController));
        m_armPivotSubsystem.setDefaultCommand(new ArmPivotJoystickCommand(m_armPivotSubsystem, m_operatorController));

        m_driverController.start().onTrue(m_chassisSubsystem.createResetGyroCommand());

        m_operatorController.x().whileTrue(m_intakeSubsystem.createMoveIntakeInCommand());
        m_operatorController.a().whileTrue(m_intakeSubsystem.createMoveIntakeOutCommand());
        m_operatorController.b().whileTrue(m_intakeSubsystem.createStopIntakeCommand());

        m_operatorController.leftBumper().whileTrue(m_shooterSubsystem.createSetRPMCommand(-ShooterSubsystem.SHOOTER_SPEED.getValue()));
        m_operatorController.leftTrigger().whileTrue(m_shooterSubsystem.createSetRPMCommand(ShooterSubsystem.SHOOTER_SPEED.getValue()));
        m_operatorController.y().whileTrue(m_shooterSubsystem.createStopShooterCommand());
    }


    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An example command will be run in autonomous
        return m_autonomousFactory.getSelectedAutonomous();
    }

    private class SuperstructureSendable implements Sendable {

        @Override
        public void initSendable(SendableBuilder builder) {
            builder.setSmartDashboardType(SmartDashboardNames.SUPER_STRUCTURE);

            builder.addDoubleProperty(
                SmartDashboardNames.PIVOT_MOTOR_ANGLE, m_armPivotSubsystem::getAngle, null);
            builder.addDoubleProperty(
                SmartDashboardNames.GOAL_ANGLE, m_armPivotSubsystem::getArmAngleGoal, null);
            builder.addDoubleProperty(
                SmartDashboardNames.SHOOTER_MOTOR_PERCENTAGE, m_shooterSubsystem::getShooterMotorPercentage, null);
            builder.addDoubleProperty(
                SmartDashboardNames.PIVOT_MOTOR_PERCENTAGE, m_armPivotSubsystem::getPivotMotorPercentage, null);
            builder.addBooleanProperty(
                SmartDashboardNames.HAS_GAME_PIECE, m_intakeSubsystem::hasGamePiece, null);
            builder.addDoubleProperty(
                SmartDashboardNames.INTAKE_MOTOR_PERCENTAGE, m_intakeSubsystem::getIntakeMotorPercentage, null);

        }
    }

}
