// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.crescendo2024;

import com.gos.crescendo2024.auton.Autos;
import com.gos.crescendo2024.commands.ArmPivotJoystickCommand;
import com.gos.crescendo2024.commands.CombinedCommands;
import com.gos.crescendo2024.commands.DavidDriveSwerve;
import com.gos.crescendo2024.commands.TeleopSwerveDrive;
import com.gos.crescendo2024.commands.TurnToPointSwerveDrive;
import com.gos.crescendo2024.subsystems.ArmPivotSubsystem;
import com.gos.crescendo2024.subsystems.ChassisSubsystem;
import com.gos.crescendo2024.subsystems.IntakeSubsystem;
import com.gos.crescendo2024.subsystems.LedManagerSubsystem;
import com.gos.crescendo2024.subsystems.ShooterSubsystem;
import com.gos.crescendo2024.subsystems.sysid.ArmPivotSysId;
import com.gos.crescendo2024.subsystems.sysid.ShooterSysId;
import com.gos.lib.properties.PropertyManager;
import edu.wpi.first.hal.AllianceStationID;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.simulation.DriverStationSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import org.photonvision.PhotonCamera;


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
    private final LedManagerSubsystem m_ledSubsystem; // NOPMD

    // SysId
    private final ArmPivotSysId m_armPivotSysId;
    private final ShooterSysId m_shooterSysId;

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
        m_autonomousFactory = new Autos(m_chassisSubsystem, m_armPivotSubsystem, m_intakeSubsystem, m_shooterSubsystem);
        m_ledSubsystem = new LedManagerSubsystem(m_intakeSubsystem, m_autonomousFactory);

        m_shooterSysId = new ShooterSysId(m_shooterSubsystem);
        m_armPivotSysId = new ArmPivotSysId(m_armPivotSubsystem);


        // Configure the trigger bindings
        configureBindings();

        createTestCommands();
        createSysIdCommands();

        SmartDashboard.putData("super structure", new SuperstructureSendable());

        if (RobotBase.isSimulation()) {
            DriverStationSim.setAllianceStationId(AllianceStationID.Blue1);
            DriverStationSim.setDsAttached(true);
            DriverStationSim.setEnabled(true);
        }

        PathPlannerUtils.createTrajectoriesShuffleboardTab(m_chassisSubsystem);

        PhotonCamera.setVersionCheckEnabled(false); // TODO turn back on when we have the cameras hooked up
    }

    private void createTestCommands() {
        ShuffleboardTab shuffleboardTab = Shuffleboard.getTab("test commands");

        addChassisTestCommands(shuffleboardTab);
        addArmPivotTestCommands(shuffleboardTab);
        addIntakeTestCommands(shuffleboardTab);
        addShooterTestCommands(shuffleboardTab);

        shuffleboardTab.add("Teleop: David Drive", new DavidDriveSwerve(m_chassisSubsystem, m_driverController));
        shuffleboardTab.add("Teleop: Normal Swerve Drive", new TeleopSwerveDrive(m_chassisSubsystem, m_driverController));
        shuffleboardTab.add("Teleop: Aim at speaker (current pose)", new TurnToPointSwerveDrive(m_chassisSubsystem, m_driverController, FieldConstants.Speaker.CENTER_SPEAKER_OPENING, true, m_chassisSubsystem::getPose));
        shuffleboardTab.add("Teleop: Aim at speaker (predicted pose)", new TurnToPointSwerveDrive(m_chassisSubsystem, m_driverController, FieldConstants.Speaker.CENTER_SPEAKER_OPENING, true, m_chassisSubsystem::getFuturePose));

        // Combined Commands
        shuffleboardTab.add("Auto-Intake Piece", CombinedCommands.intakePieceCommand(m_armPivotSubsystem, m_intakeSubsystem));
        shuffleboardTab.add("Auto-Shoot in Speaker", CombinedCommands.speakerAimAndShoot(m_armPivotSubsystem, m_shooterSubsystem, m_chassisSubsystem, m_intakeSubsystem));
        shuffleboardTab.add("Auto-Shoot in Amp", CombinedCommands.ampShooterCommand(m_armPivotSubsystem, m_shooterSubsystem, m_intakeSubsystem));

        shuffleboardTab.add("Clear Sticky Faults", Commands.run(this::resetStickyFaults).ignoringDisable(true));
    }

    private void createSysIdCommands() {
        ShuffleboardTab shuffleboardTab = Shuffleboard.getTab("SysId");


        shuffleboardTab.add("Arm SysId Dynamic Forward", m_armPivotSysId.sysIdDynamic(SysIdRoutine.Direction.kForward).withName("Arm Dyn F"));
        shuffleboardTab.add("Arm SysId Dynamic Backward", m_armPivotSysId.sysIdDynamic(SysIdRoutine.Direction.kReverse).withName("Arm Dyn B"));
        shuffleboardTab.add("Arm SysId Quasistatic Forward", m_armPivotSysId.sysIdQuasistatic(SysIdRoutine.Direction.kForward).withName("Arm Quas F"));
        shuffleboardTab.add("Arm SysId Quasistatic Backward", m_armPivotSysId.sysIdQuasistatic(SysIdRoutine.Direction.kReverse).withName("Arm Quas B"));

        shuffleboardTab.add("Shooter SysId Dynamic Forward", m_shooterSysId.sysIdQuasistatic(SysIdRoutine.Direction.kForward).withName("Shooter Dyn F"));
        shuffleboardTab.add("Shooter SysId Dynamic Backward", m_shooterSysId.sysIdQuasistatic(SysIdRoutine.Direction.kReverse).withName("Shooter Dyn B"));
        shuffleboardTab.add("Shooter SysId Quasistatic Forward", m_shooterSysId.sysIdDynamic(SysIdRoutine.Direction.kForward).withName("Shooter Quas F"));
        shuffleboardTab.add("Shooter SysId Quasistatic Backward", m_shooterSysId.sysIdDynamic(SysIdRoutine.Direction.kReverse).withName("Shooter Quas B"));
    }

    private void addChassisTestCommands(ShuffleboardTab shuffleboardTab) {
        shuffleboardTab.add("Chassis to 45", m_chassisSubsystem.createTurnToAngleCommand(45));
        shuffleboardTab.add("Chassis to 90", m_chassisSubsystem.createTurnToAngleCommand(90));
        shuffleboardTab.add("Chassis to -180", m_chassisSubsystem.createTurnToAngleCommand(-180));
        shuffleboardTab.add("Chassis to -45", m_chassisSubsystem.createTurnToAngleCommand(-45));

        shuffleboardTab.add("Chassis Push", m_chassisSubsystem.createPushForwardModeCommand());


        shuffleboardTab.add("Chassis Set Pose Origin", m_chassisSubsystem.createResetPoseCommand(new Pose2d(0, 0, Rotation2d.fromDegrees(0))));
        shuffleboardTab.add("Chassis Set Pose Subwoofer Bottom", m_chassisSubsystem.createResetPoseCommand(new Pose2d(0.6933452953924437, 4.403274541213847, Rotation2d.fromDegrees(-60))).withName("Reset Pose Subwoofer Bottom"));
        shuffleboardTab.add("Chassis Set Pose Subwoofer Mid", m_chassisSubsystem.createResetPoseCommand(new Pose2d(1.34, 5.55, Rotation2d.fromDegrees(0))).withName("Reset Pose Subwoofer Mid"));
        shuffleboardTab.add("Chassis Set Pose Subwoofer Top", m_chassisSubsystem.createResetPoseCommand(new Pose2d(0.6933452953924437,        6.686887667641241, Rotation2d.fromDegrees(60))).withName("Reset Pose Subwoofer Top"));

        shuffleboardTab.add("Chassis drive to speaker", m_chassisSubsystem.createDriveToPointCommand(FieldConstants.Speaker.CENTER_SPEAKER_OPENING).withName("Drive To Speaker"));
        shuffleboardTab.add("Chassis drive to amp", m_chassisSubsystem.createDriveToPointCommand(new Pose2d(FieldConstants.AMP_CENTER, Rotation2d.fromDegrees(90))).withName("Drive To Amp"));
    }

    private void addIntakeTestCommands(ShuffleboardTab shuffleboardTab) {
        shuffleboardTab.add("intake in", m_intakeSubsystem.createMoveIntakeInCommand());
        shuffleboardTab.add("intake out", m_intakeSubsystem.createMoveIntakeOutCommand());
    }

    private void addArmPivotTestCommands(ShuffleboardTab shuffleboardTab) {
        shuffleboardTab.add("Arm to 0", m_armPivotSubsystem.createMoveArmToAngleCommand(0));
        shuffleboardTab.add("Arm to 30", m_armPivotSubsystem.createMoveArmToAngleCommand(30));
        shuffleboardTab.add("Arm to 45", m_armPivotSubsystem.createMoveArmToAngleCommand(45));
        shuffleboardTab.add("Arm to 60", m_armPivotSubsystem.createMoveArmToAngleCommand(60));
        shuffleboardTab.add("Arm to 90", m_armPivotSubsystem.createMoveArmToAngleCommand(90));
        shuffleboardTab.add("Arm to coast", m_armPivotSubsystem.createPivotToCoastModeCommand());
        shuffleboardTab.add("Arm to amp angle", m_armPivotSubsystem.createMoveArmToAmpAngleCommand());
        shuffleboardTab.add("Arm to ground intake angle", m_armPivotSubsystem.createMoveArmToGroundIntakeAngleCommand());
        shuffleboardTab.add("Arm to default speaker angle", m_armPivotSubsystem.createMoveArmToDefaultSpeakerAngleCommand());
        shuffleboardTab.add("Arm to speaker (from pose)", m_armPivotSubsystem.createPivotUsingSpeakerTableCommand(m_chassisSubsystem::getPose));
        shuffleboardTab.add("Arm Resync Encoder", m_armPivotSubsystem.createSyncRelativeEncoderCommand());

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
        } else {
            m_chassisSubsystem.setDefaultCommand(new TeleopSwerveDrive(m_chassisSubsystem, m_driverController));
        }
        m_armPivotSubsystem.setDefaultCommand(new ArmPivotJoystickCommand(m_armPivotSubsystem, m_operatorController));

        /////////////////////////////
        // Driver Controller
        /////////////////////////////
        // Chassis
        m_driverController.start().and(m_driverController.back())
            .whileTrue(m_chassisSubsystem.createResetGyroCommand());
        m_driverController.x().whileTrue(new TurnToPointSwerveDrive(m_chassisSubsystem, m_driverController, FieldConstants.Speaker.CENTER_SPEAKER_OPENING, true, m_chassisSubsystem::getPose));

        // Amp Scoring
        m_driverController.leftBumper().whileTrue(CombinedCommands.prepareAmpShot(m_armPivotSubsystem, m_shooterSubsystem));
        m_driverController.leftBumper().and(m_driverController.rightTrigger()).whileTrue(
            CombinedCommands.ampShooterCommand(m_armPivotSubsystem, m_shooterSubsystem, m_intakeSubsystem));

        //Speaker Shooting
        m_driverController.rightBumper().whileTrue(
            CombinedCommands.prepareSpeakerShot(m_armPivotSubsystem, m_shooterSubsystem));

        m_driverController.rightBumper().and(m_driverController.rightTrigger()).whileTrue(
            CombinedCommands.prepareSpeakerShot(m_armPivotSubsystem, m_shooterSubsystem)
                    .alongWith(m_intakeSubsystem.createMoveIntakeInCommand()));

        //go to floor
        m_driverController.leftTrigger().whileTrue(CombinedCommands.intakePieceCommand(m_armPivotSubsystem, m_intakeSubsystem));
        //spit out
        m_driverController.a().whileTrue(m_intakeSubsystem.createMoveIntakeOutCommand());


        /////////////////////////////
        // Operator Controller
        /////////////////////////////
        // Intake
        m_operatorController.leftBumper().whileTrue(m_intakeSubsystem.createIntakeUntilPieceCommand());
        m_operatorController.leftTrigger().whileTrue(m_intakeSubsystem.createMoveIntakeInCommand());
        m_operatorController.rightBumper().whileTrue(m_intakeSubsystem.createMoveIntakeOutCommand());


        // shooter
        m_operatorController.rightTrigger().whileTrue(m_shooterSubsystem.createRunDefaultRpmCommand());

        PropertyManager.printDynamicProperties();
        // PropertyManager.purgeExtraKeys();
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

    private void resetStickyFaults() {
        m_chassisSubsystem.clearStickyFaults();
        m_armPivotSubsystem.clearStickyFaults();
        m_intakeSubsystem.clearStickyFaults();
        m_shooterSubsystem.clearStickyFaults();
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
