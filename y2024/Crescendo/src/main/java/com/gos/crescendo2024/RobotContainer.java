// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.crescendo2024;

import com.gos.crescendo2024.auton.Autos;
import com.gos.crescendo2024.commands.ArmPivotJoystickCommand;
import com.gos.crescendo2024.commands.CombinedCommands;
import com.gos.crescendo2024.commands.DavidDriveSwerve;
import com.gos.crescendo2024.commands.SpeakerAimAndShootCommand;
import com.gos.crescendo2024.commands.TeleopSwerveDrive;
import com.gos.crescendo2024.commands.TurnToPointSwerveDrive;
import com.gos.crescendo2024.subsystems.ArmPivotSubsystem;
import com.gos.crescendo2024.subsystems.ChassisSubsystem;
import com.gos.crescendo2024.subsystems.IntakeSubsystem;
import com.gos.crescendo2024.subsystems.ShooterSubsystem;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.hal.AllianceStationID;
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

        NamedCommands.registerCommand("AimAndShootIntoSpeaker", new SpeakerAimAndShootCommand(m_armPivotSubsystem, m_chassisSubsystem, m_intakeSubsystem, m_shooterSubsystem));
        NamedCommands.registerCommand("IntakePiece", CombinedCommands.intakePieceCommand(m_armPivotSubsystem, m_intakeSubsystem).withTimeout(1));
        NamedCommands.registerCommand("MoveArmToSpeakerAngle", m_armPivotSubsystem.createMoveArmToDefaultSpeakerAngleCommand());
        NamedCommands.registerCommand("ShooterDefaultRpm", m_shooterSubsystem.createRunDefaultRpmCommand());
        m_autonomousFactory = new Autos();

        // Configure the trigger bindings
        configureBindings();

        createTestCommands();

        SmartDashboard.putData("super structure", new SuperstructureSendable());

        if (RobotBase.isSimulation()) {
            DriverStationSim.setAllianceStationId(AllianceStationID.Blue1);
            DriverStationSim.setDsAttached(true);
            DriverStationSim.setEnabled(true);
        }

        PathPlannerUtils.createTrajectoriesShuffleboardTab(m_chassisSubsystem);
    }

    private void createTestCommands() {
        ShuffleboardTab shuffleboardTab = Shuffleboard.getTab("test commands");

        addChassisTestCommands(shuffleboardTab);
        addArmPivotTestCommands(shuffleboardTab);
        addIntakeTestCommands(shuffleboardTab);
        addShooterTestCommands(shuffleboardTab);

        shuffleboardTab.add("Teleop: David Drive", new DavidDriveSwerve(m_chassisSubsystem, m_driverController));
        shuffleboardTab.add("Teleop: Aim at speaker", new TurnToPointSwerveDrive(m_chassisSubsystem, m_driverController, FieldConstants.Speaker.CENTER_SPEAKER_OPENING));

        // Combined Commands
        shuffleboardTab.add("Auto-Intake Piece", CombinedCommands.intakePieceCommand(m_armPivotSubsystem, m_intakeSubsystem));
        shuffleboardTab.add("Auto-Shoot in Speaker", CombinedCommands.speakerAimAndShoot(m_armPivotSubsystem, m_shooterSubsystem, m_chassisSubsystem, m_intakeSubsystem));
        shuffleboardTab.add("Auto-Shoot in Amp", CombinedCommands.ampShooterCommand(m_armPivotSubsystem, m_intakeSubsystem));
    }

    private void addChassisTestCommands(ShuffleboardTab shuffleboardTab) {
        shuffleboardTab.add("Chassis to 45", m_chassisSubsystem.createTurnToAngleCommand(45));
        shuffleboardTab.add("Chassis to 90", m_chassisSubsystem.createTurnToAngleCommand(90));
        shuffleboardTab.add("Chassis to -180", m_chassisSubsystem.createTurnToAngleCommand(-180));
        shuffleboardTab.add("Chassis to -45", m_chassisSubsystem.createTurnToAngleCommand(-45));

        shuffleboardTab.add("Chassis drive to speaker", m_chassisSubsystem.createDriveToPointCommand(FieldConstants.Speaker.CENTER_SPEAKER_OPENING).withName("Drive To Speaker"));
        shuffleboardTab.add("Chassis drive to amp", m_chassisSubsystem.createDriveToPointCommand(new Pose2d(FieldConstants.AMP_CENTER, Rotation2d.fromDegrees(90))).withName("Drive To Amp"));
    }

    private void addIntakeTestCommands(ShuffleboardTab shuffleboardTab) {
        shuffleboardTab.add("intake in", m_intakeSubsystem.createMoveIntakeInCommand());
        shuffleboardTab.add("intake out", m_intakeSubsystem.createMoveIntakeOutCommand());
    }

    private void addArmPivotTestCommands(ShuffleboardTab shuffleboardTab) {
        shuffleboardTab.add("Arm to 0", m_armPivotSubsystem.createMoveArmToAngleCommand(0));
        shuffleboardTab.add("Arm to 45", m_armPivotSubsystem.createMoveArmToAngleCommand(45));
        shuffleboardTab.add("Arm to 90", m_armPivotSubsystem.createMoveArmToAngleCommand(90));
        shuffleboardTab.add("Arm to coast", m_armPivotSubsystem.createPivotToCoastModeCommand());
        shuffleboardTab.add("Arm to amp angle", m_armPivotSubsystem.createMoveArmToAmpAngleCommand());
        shuffleboardTab.add("Arm to ground intake angle", m_armPivotSubsystem.createMoveArmToGroundIntakeAngleCommand());
        shuffleboardTab.add("Arm to default speaker angle", m_armPivotSubsystem.createMoveArmToDefaultSpeakerAngleCommand());
        shuffleboardTab.add("Arm to speaker (from pose)", m_armPivotSubsystem.createPivotUsingSpeakerTableCommand(m_chassisSubsystem::getPose));
    }

    private void addShooterTestCommands(ShuffleboardTab shuffleboardTab) {
        shuffleboardTab.add("Shooter Percent Output", m_shooterSubsystem.createTunePercentShootCommand());
        shuffleboardTab.add("shooter RPM: default", m_shooterSubsystem.createRunDefaultRpmCommand());
        shuffleboardTab.add("Shooter RPM: 3500", m_shooterSubsystem.createSetRPMCommand(3500));
        shuffleboardTab.add("Shooter RPM: 4000", m_shooterSubsystem.createSetRPMCommand(4000));
        shuffleboardTab.add("Shooter stop", m_shooterSubsystem.createStopShooterCommand());
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
        /////////////////////////////
        // Default Commands
        /////////////////////////////
        if (RobotBase.isReal()) {
            m_chassisSubsystem.setDefaultCommand(new DavidDriveSwerve(m_chassisSubsystem, m_driverController));
        }
        else {
            m_chassisSubsystem.setDefaultCommand(new TeleopSwerveDrive(m_chassisSubsystem, m_driverController));
        }
        m_armPivotSubsystem.setDefaultCommand(new ArmPivotJoystickCommand(m_armPivotSubsystem, m_operatorController));

        /////////////////////////////
        // Driver Controller
        /////////////////////////////
        // Chassis
        m_driverController.start().onTrue(m_chassisSubsystem.createResetGyroCommand());
        m_driverController.x().whileTrue(new TurnToPointSwerveDrive(m_chassisSubsystem, m_driverController, FieldConstants.Speaker.CENTER_SPEAKER_OPENING));

        // Arm
        m_driverController.leftTrigger().onTrue(m_armPivotSubsystem.createMoveArmToGroundIntakeAngleCommand());
        m_driverController.leftBumper().whileTrue(m_armPivotSubsystem.createMoveArmToAmpAngleCommand());
        m_driverController.rightBumper().whileTrue(m_armPivotSubsystem.createMoveArmToDefaultSpeakerAngleCommand());

        // This is used to shoot
        m_driverController.rightTrigger().whileTrue(m_intakeSubsystem.createMoveIntakeInCommand());

        /////////////////////////////
        // Operator Controller
        /////////////////////////////
        // Intake
        m_operatorController.rightBumper().whileTrue(m_intakeSubsystem.createMoveIntakeInCommand());
        m_operatorController.leftBumper().whileTrue(m_intakeSubsystem.createMoveIntakeOutCommand());

        // shooter
        m_operatorController.rightTrigger().whileTrue(m_shooterSubsystem.createTunePercentShootCommand());
        m_operatorController.a().whileTrue(m_shooterSubsystem.createStopShooterCommand());
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
