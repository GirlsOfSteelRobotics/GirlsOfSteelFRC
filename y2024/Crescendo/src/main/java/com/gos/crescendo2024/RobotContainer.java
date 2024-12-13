// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.crescendo2024;

import com.gos.crescendo2024.auton.Autos;
import com.gos.crescendo2024.auton.GosAutoMode;
import com.gos.crescendo2024.commands.ArmPivotJoystickCommand;
import com.gos.crescendo2024.commands.CombinedCommands;
import com.gos.crescendo2024.commands.DavidDriveSwerve;
import com.gos.crescendo2024.commands.FeedPiecesWithVision;
import com.gos.crescendo2024.commands.SpeakerAimAndShootCommand;
import com.gos.crescendo2024.commands.TeleopSwerveDrive;
import com.gos.crescendo2024.commands.TurnToPointSwerveDrive;
import com.gos.crescendo2024.commands.VibrateControllerTimedCommand;
import com.gos.crescendo2024.subsystems.ArmPivotSubsystem;
import com.gos.crescendo2024.subsystems.ChassisSubsystem;
import com.gos.crescendo2024.subsystems.HangerSubsystem;
import com.gos.crescendo2024.subsystems.IntakeSubsystem;
import com.gos.crescendo2024.subsystems.LedManagerSubsystem;
import com.gos.crescendo2024.subsystems.ShooterSubsystem;
import com.gos.crescendo2024.subsystems.sysid.ArmPivotSysId;
import com.gos.crescendo2024.subsystems.sysid.ShooterSysId;
import com.gos.lib.properties.GosBooleanProperty;
import com.gos.lib.properties.PropertyManager;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.hal.AllianceStationID;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.simulation.DriverStationSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import org.photonvision.PhotonCamera;

import java.util.Set;

import static edu.wpi.first.wpilibj2.command.Commands.defer;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class RobotContainer {
    private static final GosBooleanProperty USE_VISION = new GosBooleanProperty(false, "Use Vision for driving", true);

    private static final boolean HAS_HANGER = true;

    // Subsystems
    private final ChassisSubsystem m_chassisSubsystem;
    private final ArmPivotSubsystem m_armPivotSubsystem;
    private final ShooterSubsystem m_shooterSubsystem;
    private final IntakeSubsystem m_intakeSubsystem;
    private final LedManagerSubsystem m_ledSubsystem; // NOPMD
    private final HangerSubsystem m_hangerSubsystem;

    private final CombinedCommands m_combinedCommands;

    // SysId
    private final ArmPivotSysId m_armPivotSysId;
    private final ShooterSysId m_shooterSysId;

    // Joysticks
    private final CommandXboxController m_driverController =
        new CommandXboxController(Constants.DRIVER_JOYSTICK);

    private final CommandXboxController m_operatorController =
        new CommandXboxController(Constants.OPERATOR_JOYSTICK);

    private final DriverStationLedDriver m_driverStationLedController = new DriverStationLedDriver(2);

    private final Autos m_autonomousFactory;

    private final PowerDistribution m_pdh;


    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer(PowerDistribution pdh) {
        m_pdh = pdh;

        m_chassisSubsystem = new ChassisSubsystem();
        if (!Constants.IS_TIM_BOT) {
            m_shooterSubsystem = new ShooterSubsystem();
            m_armPivotSubsystem = new ArmPivotSubsystem();
            m_intakeSubsystem = new IntakeSubsystem();
            m_shooterSysId = new ShooterSysId(m_shooterSubsystem);
            m_armPivotSysId = new ArmPivotSysId(m_armPivotSubsystem);
            if (HAS_HANGER) {
                m_hangerSubsystem = new HangerSubsystem();
            } else {
                m_hangerSubsystem = null;
            }
        }
        else {
            m_shooterSubsystem = null;
            m_armPivotSubsystem = null;
            m_intakeSubsystem = null;
            m_shooterSysId = null;
            m_armPivotSysId = null;
            m_hangerSubsystem = null;
        }

        m_combinedCommands = new CombinedCommands(m_chassisSubsystem, m_armPivotSubsystem, m_shooterSubsystem, m_intakeSubsystem, m_hangerSubsystem);


        if (!Constants.IS_TIM_BOT) {
            NamedCommands.registerCommand("TrustVision", m_chassisSubsystem.createBelieveAprilTagEstimatorCommand());
            NamedCommands.registerCommand("AimAndShootIntoSpeaker", SpeakerAimAndShootCommand.createShootWhileStationary(m_armPivotSubsystem, m_chassisSubsystem, m_intakeSubsystem, m_shooterSubsystem));
            NamedCommands.registerCommand("AimAndShootIntoSpeakerWhileDrive", SpeakerAimAndShootCommand.createShootWhileDrive(m_armPivotSubsystem, m_chassisSubsystem, m_intakeSubsystem, m_shooterSubsystem));
            NamedCommands.registerCommand("AimAndShootIntoSideSpeaker", SpeakerAimAndShootCommand.createWithFixedArmAngle(m_armPivotSubsystem, m_chassisSubsystem, m_intakeSubsystem, m_shooterSubsystem, ArmPivotSubsystem.SIDE_SUBWOOFER_ANGLE::getValue));
            NamedCommands.registerCommand("IntakePiece", m_combinedCommands.intakePieceCommand());
            NamedCommands.registerCommand("MoveArmToSpeakerAngle", m_armPivotSubsystem.createPivotUsingSpeakerTableCommand(m_chassisSubsystem::getPose));
            NamedCommands.registerCommand("ShooterDefaultRpm", m_shooterSubsystem.createRunSpeakerShotRPMCommand());
            NamedCommands.registerCommand("AimAndShootIntoSpeakerTopSpike", SpeakerAimAndShootCommand.createWithFixedArmAngle(m_armPivotSubsystem, m_chassisSubsystem, m_intakeSubsystem, m_shooterSubsystem, ArmPivotSubsystem.SPIKE_TOP_ANGLE::getValue));
            NamedCommands.registerCommand("AimAndShootIntoSpeakerMiddleSpike", SpeakerAimAndShootCommand.createWithFixedArmAngle(m_armPivotSubsystem, m_chassisSubsystem, m_intakeSubsystem, m_shooterSubsystem, ArmPivotSubsystem.SPIKE_MIDDLE_ANGLE::getValue));
            NamedCommands.registerCommand("AimAndShootIntoSpeakerBottomSpike", SpeakerAimAndShootCommand.createWithFixedArmAngle(m_armPivotSubsystem, m_chassisSubsystem, m_intakeSubsystem, m_shooterSubsystem, ArmPivotSubsystem.SPIKE_BOTTOM_ANGLE::getValue));
            NamedCommands.registerCommand("PrepSpeakerShot", m_combinedCommands.prepareSpeakerShot(m_chassisSubsystem::getPose));
            NamedCommands.registerCommand("Arm down", m_armPivotSubsystem.createMoveArmToAngleCommand(0).until(m_armPivotSubsystem::isArmAtGoal));
        }

        m_autonomousFactory = new Autos(m_combinedCommands);

        if (!Constants.IS_TIM_BOT) {
            m_ledSubsystem = new LedManagerSubsystem(m_driverStationLedController, m_intakeSubsystem, m_autonomousFactory, m_chassisSubsystem, m_armPivotSubsystem, m_shooterSubsystem);
        } else {
            m_ledSubsystem = null;
        }

        if (!Constants.IS_TIM_BOT) {
            // Configure the trigger bindings
            configureBindings();

            // These three should be off for competition
            createTestCommands();
            createSysIdCommands();
            // PathPlannerUtils.createTrajectoriesShuffleboardTab(m_chassisSubsystem);

            createEllieCommands();

            SmartDashboard.putData("super structure", new SuperstructureSendable());
        } else {
            m_chassisSubsystem.setDefaultCommand(new TeleopSwerveDrive(m_chassisSubsystem, m_driverController));
            m_driverController.start().and(m_driverController.back())
                .whileTrue(m_chassisSubsystem.createResetGyroCommand());
        }

        if (RobotBase.isSimulation()) {
            DriverStationSim.setAllianceStationId(AllianceStationID.Blue1);
            DriverStationSim.setDsAttached(true);
            DriverStationSim.setEnabled(true);
        }

        PhotonCamera.setVersionCheckEnabled(false); // TODO turn back on when we have the cameras hooked up

        SmartDashboard.putBoolean("Is Competition Robot", Constants.IS_COMPETITION_ROBOT);
        SmartDashboard.putBoolean("Is Tim Bot", Constants.IS_TIM_BOT);

        createTestLedsCommands();

        PropertyManager.printDynamicProperties();
        // PropertyManager.purgeExtraKeys();

        DriverStation.silenceJoystickConnectionWarning(true);
    }

    private void createTestLedsCommands() {
        ShuffleboardTab tab = Shuffleboard.getTab("Leds");
        for (int i = 0; i < 32; ++i) {
            final int bit = i;
            tab.add("Raw Bit " + i,
                Commands.startEnd(() -> {
                    m_driverStationLedController.setBit(bit, true);
                    m_driverStationLedController.write();
                }, () -> {
                    m_driverStationLedController.setBit(bit, false);
                    m_driverStationLedController.write();
                })
                .withName("Set Raw Bit " + bit));
        }

        for (int i = 0; i < 16; ++i) {
            final int bit = i;
            DriverStationLedDriver.BitField field = DriverStationLedDriver.BitField.valueOf("ARDUINO_BIT_" + bit);
            tab.add("Arduino Bit " + i,
                Commands.startEnd(() -> {
                    m_driverStationLedController.setBit(field, true);
                    m_driverStationLedController.write();
                }, () -> {
                    m_driverStationLedController.setBit(field, false);
                    m_driverStationLedController.write();
                })
                .withName("Set Arduino Bit " + bit));
        }
    }

    private void createEllieCommands() {
        ShuffleboardTab shuffleboardTab = Shuffleboard.getTab("Ellie");

        shuffleboardTab.add("Arm to coast", m_armPivotSubsystem.createPivotToCoastModeCommand());
        shuffleboardTab.add("Arm Resync Encoder", m_armPivotSubsystem.createSyncRelativeEncoderCommand());
        shuffleboardTab.add("Clear Sticky Faults", Commands.run(this::resetStickyFaults).ignoringDisable(true).withName("Clear Sticky Faults"));
        shuffleboardTab.add("Chassis Set Pose Subwoofer Mid", m_chassisSubsystem.createResetPoseCommand(RobotExtrinsics.STARTING_POSE_MIDDLE_SUBWOOFER).withName("Reset Pose Subwoofer Mid"));
        shuffleboardTab.add("Chassis: Believe April Tags", m_chassisSubsystem.createBelieveAprilTagEstimatorCommand());

        if (HAS_HANGER) {
            addHangerTestCommands(shuffleboardTab);

            shuffleboardTab.add("Prep Hanger Up", m_combinedCommands.prepHangingUp(m_operatorController));
        }
    }

    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private void createTestCommands() {
        ShuffleboardTab shuffleboardTab = Shuffleboard.getTab("test commands");

        addChassisTestCommands(shuffleboardTab);
        addArmPivotTestCommands(shuffleboardTab);
        addIntakeTestCommands(shuffleboardTab);
        addShooterTestCommands(shuffleboardTab);
        if (HAS_HANGER) {
            addHangerTestCommands(shuffleboardTab);
        }

        shuffleboardTab.add("Teleop: David Drive", new DavidDriveSwerve(m_chassisSubsystem, m_driverController));
        shuffleboardTab.add("Teleop: Normal Swerve Drive", new TeleopSwerveDrive(m_chassisSubsystem, m_driverController));
        shuffleboardTab.add("Teleop: Aim at speaker (current pose)", new TurnToPointSwerveDrive(m_chassisSubsystem, m_driverController, FieldConstants.Speaker.CENTER_SPEAKER_OPENING, true, m_chassisSubsystem::getPose).withName("Turn To Speaker (current)"));
        shuffleboardTab.add("Teleop: Aim at speaker (predicted pose)", new TurnToPointSwerveDrive(m_chassisSubsystem, m_driverController, FieldConstants.Speaker.CENTER_SPEAKER_OPENING, true, m_chassisSubsystem::getFuturePose).withName("Turn To Speaker (prediction)"));

        //SpeakerAimAndShootCommands:
        shuffleboardTab.add("SpeakerAimAndShoot Default Values", SpeakerAimAndShootCommand.createWithDefaults(m_armPivotSubsystem, m_chassisSubsystem, m_intakeSubsystem, m_shooterSubsystem).withName("AimAndShoot w/ defaults"));
        shuffleboardTab.add("SpeakerAimAndShoot Fixed Values", SpeakerAimAndShootCommand.createWithFixedArmAngle(m_armPivotSubsystem, m_chassisSubsystem, m_intakeSubsystem, m_shooterSubsystem, () -> 20).withName("AimAndShoot @ 20deg"));
        shuffleboardTab.add("SpeakerAimAndShoot Shoot While Driving", SpeakerAimAndShootCommand.createShootWhileDrive(m_armPivotSubsystem, m_chassisSubsystem, m_intakeSubsystem, m_shooterSubsystem).withName("AimAndShoot on the move"));
        shuffleboardTab.add("SpeakerAimAndShoot Shoot While Stationary", SpeakerAimAndShootCommand.createShootWhileStationary(m_armPivotSubsystem, m_chassisSubsystem, m_intakeSubsystem, m_shooterSubsystem).withName("AimAndShoot with lookup"));

        // Combined Commands
        shuffleboardTab.add("Auto-Intake Piece", m_combinedCommands.intakePieceCommand());
        shuffleboardTab.add("Auto-Shoot in Amp", m_combinedCommands.ampShooterCommand());
        shuffleboardTab.add("Full Field Feed", m_combinedCommands.feedPieceAcrossFieldWithVision(m_driverController));
        shuffleboardTab.add("Prepare Tunable Shot", m_combinedCommands.prepareTunableShot());

        shuffleboardTab.add("Clear Sticky Faults", Commands.run(this::resetStickyFaults).ignoringDisable(true).withName("Clear Sticky Faults"));
    }

    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private void createSysIdCommands() {
        ShuffleboardTab shuffleboardTab = Shuffleboard.getTab("SysId");

        shuffleboardTab.add("Arm SysId", m_armPivotSysId.createSysidRoutineCommand());
        shuffleboardTab.add("Shooter SysId", m_shooterSysId.createSysidRoutineCommand());
    }

    private void addChassisTestCommands(ShuffleboardTab shuffleboardTab) {
        shuffleboardTab.add("Chassis to 45", m_chassisSubsystem.createTurnToAngleCommand(45));
        shuffleboardTab.add("Chassis to 90", m_chassisSubsystem.createTurnToAngleCommand(90));
        shuffleboardTab.add("Chassis to -180", m_chassisSubsystem.createTurnToAngleCommand(-180));
        shuffleboardTab.add("Chassis to -45", m_chassisSubsystem.createTurnToAngleCommand(-45));

        shuffleboardTab.add("Chassis Push Forward", m_chassisSubsystem.createPushForwardModeCommand());
        shuffleboardTab.add("Chassis Push Sideways", m_chassisSubsystem.createPushSidewaysModeCommand());


        shuffleboardTab.add("Chassis Set Pose Origin", m_chassisSubsystem.createResetPoseCommand(new Pose2d(0, 0, Rotation2d.fromDegrees(0))));
        shuffleboardTab.add("Chassis Set Pose Subwoofer Bottom", m_chassisSubsystem.createResetPoseCommand(RobotExtrinsics.STARTING_POSE_SOURCE_SUBWOOFER).withName("Reset Pose Subwoofer Bottom"));
        shuffleboardTab.add("Chassis Set Pose Subwoofer Mid Blue", m_chassisSubsystem.createResetPoseCommand(RobotExtrinsics.STARTING_POSE_MIDDLE_SUBWOOFER).withName("Reset Pose Subwoofer Mid Blue"));
        shuffleboardTab.add("Chassis Set Pose Subwoofer Top", m_chassisSubsystem.createResetPoseCommand(RobotExtrinsics.STARTING_POSE_AMP_SUBWOOFER).withName("Reset Pose Subwoofer Top"));

        shuffleboardTab.add("Chassis drive to amp", m_chassisSubsystem.createDriveToAmpCommand().withName("Drive To Amp"));
        shuffleboardTab.add("Chassis drive to note", m_chassisSubsystem.createDriveToNoteCommand());

        shuffleboardTab.add("Chassis Sync Odo and Est Pos", m_chassisSubsystem.createSyncOdometryAndPoseEstimatorCommand().withName("Sync Odo and Est Pos"));

        shuffleboardTab.add("Auto: Go To AutonomousStart", createGoToAutoStartingPosition().withName("move robot to auto position"));

        shuffleboardTab.add("Set Chassis Speed X 1",  m_chassisSubsystem.createSetChassisSpeedCommand(new ChassisSpeeds(1, 0, 0)).withName("Set Chassis Speed 1"));
        shuffleboardTab.add("Set Chassis Speed X 2",  m_chassisSubsystem.createSetChassisSpeedCommand(new ChassisSpeeds(2, 0, 0)).withName("Set Chassis Speed 2"));
        shuffleboardTab.add("Set Chassis Speed X 3",  m_chassisSubsystem.createSetChassisSpeedCommand(new ChassisSpeeds(3, 0, 0)).withName("Set Chassis Speed 3"));
    }

    private Command createGoToAutoStartingPosition() {
        return Commands.sequence(
            m_armPivotSubsystem.createMoveArmToGroundIntakeAngleCommand().until(() -> m_armPivotSubsystem.getAngle() < 5),
            defer(() -> m_chassisSubsystem.createPathfindToPoseCommand(getAutonomousStartingPose()), Set.of(m_chassisSubsystem)),
            m_armPivotSubsystem.createMoveArmToAngleCommand(78));
    }

    private MaybeFlippedPose2d getAutonomousStartingPose() {
        GosAutoMode.StartPosition startingLocation = m_autonomousFactory.getSelectedCommand().getStartingLocation();

        switch (startingLocation) {
        case STARTING_LOCATION_AMP_SIDE -> {
            return RobotExtrinsics.STARTING_POSE_AMP_SUBWOOFER;
        }
        case STARTING_LOCATION_MIDDLE -> {
            return RobotExtrinsics.STARTING_POSE_MIDDLE_SUBWOOFER;
        }
        case STARTING_LOCATION_SOURCE_SIDE -> {
            return RobotExtrinsics.STARTING_POSE_SOURCE_SUBWOOFER;
        }
        case STARTING_LOCATION_SOURCE_CORNER -> {
            return RobotExtrinsics.STARTING_POSE_SOURCE_CORNER;
        }
        case CURRENT_LOCATION -> {
            return new MaybeFlippedPose2d(m_chassisSubsystem.getPose());
        }
        default -> throw new IllegalArgumentException("this should never happen");
        }
    }

    private void addIntakeTestCommands(ShuffleboardTab shuffleboardTab) {
        shuffleboardTab.add("intake in", m_intakeSubsystem.createMoveIntakeInCommand());
        shuffleboardTab.add("intake out", m_intakeSubsystem.createMoveIntakeOutCommand());
        shuffleboardTab.add("intake to coast", m_intakeSubsystem.createIntakeToCoastCommand());
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
        shuffleboardTab.add("Arm to middle speaker angle", m_armPivotSubsystem.createMoveArmToMiddleSpeakerAngleCommand());
        shuffleboardTab.add("Arm to side speaker angle", m_armPivotSubsystem.createMoveArmToSideSpeakerAngleCommand());
        shuffleboardTab.add("Arm to tunable speaker angle", m_armPivotSubsystem.createMoveArmToTunableSpeakerAngleCommand());
        shuffleboardTab.add("Arm to speaker (from pose)", m_armPivotSubsystem.createPivotUsingSpeakerTableCommand(m_chassisSubsystem::getPose));
        shuffleboardTab.add("Arm Resync Encoder", m_armPivotSubsystem.createSyncRelativeEncoderCommand());
        shuffleboardTab.add("Arm to prep hanger angle", m_armPivotSubsystem.createMoveArmToPrepHangerAngleCommand());

    }

    private void addShooterTestCommands(ShuffleboardTab shuffleboardTab) {
        shuffleboardTab.add("Shooter Percent Output", m_shooterSubsystem.createTunePercentShootCommand());
        shuffleboardTab.add("Shooter RPM: Tunable", m_shooterSubsystem.createRunTunableRpmCommand());
        shuffleboardTab.add("Shooter RPM: Default Speaker Shot", m_shooterSubsystem.createRunSpeakerShotRPMCommand());
        shuffleboardTab.add("Shooter RPM: 3500", m_shooterSubsystem.createSetRPMCommand(3500));
        shuffleboardTab.add("Shooter RPM: 4000", m_shooterSubsystem.createSetRPMCommand(4000));
        shuffleboardTab.add("Shooter stop", m_shooterSubsystem.createStopShooterCommand());
        shuffleboardTab.add("Shoot note to alliance with rpm", m_shooterSubsystem.createShootNoteToAllianceRPMCommand());
    }

    private void addHangerTestCommands(ShuffleboardTab shuffleboardTab) {
        shuffleboardTab.add("Hanger Up", m_hangerSubsystem.createHangerUp().withName("Hanger Up"));
        shuffleboardTab.add("Hanger Down", m_hangerSubsystem.createHangerDown().withName("Hanger Down"));

        shuffleboardTab.add("Left Hanger Up", m_hangerSubsystem.createLeftHangerUp());
        shuffleboardTab.add("Left Hanger Down", m_hangerSubsystem.createLeftHangerDown());

        shuffleboardTab.add("Right Hanger Up", m_hangerSubsystem.createRightHangerUp());
        shuffleboardTab.add("Right Hanger Down", m_hangerSubsystem.createRightHangerDown());

        shuffleboardTab.add("Hanger to Coast", m_hangerSubsystem.createSetHangerToCoast().withName("Hanger to Coast"));

        shuffleboardTab.add("Auto Hanger Up", m_hangerSubsystem.createAutoUpCommand());
        shuffleboardTab.add("Auto Hanger Down", m_hangerSubsystem.createAutoDownCommand());

        shuffleboardTab.add("Hanger reset encoders", m_hangerSubsystem.createResetEncoders());
    }

    private boolean visionEnabled() {
        return USE_VISION.getValue();
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

        //Slow / reg chassis speed
        m_driverController.povUp().whileTrue(m_chassisSubsystem.createSetSlowModeCommand(false));
        m_driverController.povDown().whileTrue(m_chassisSubsystem.createSetSlowModeCommand(true));

        // Chassis
        m_driverController.start().and(m_driverController.back())
            .whileTrue(m_chassisSubsystem.createResetGyroCommand());

        // Intake-to-shoot
        m_driverController.rightTrigger().whileTrue(m_intakeSubsystem.createMoveIntakeInCommand());

        // Amp Scoring
        m_driverController.leftBumper().whileTrue(
            m_combinedCommands.prepareAmpShot()
                .alongWith(m_combinedCommands.vibrateIfReadyToShoot(m_driverController)));

        //go to floor
        m_driverController.leftTrigger().whileTrue(
            m_combinedCommands.intakePieceCommand()
                .andThen(new VibrateControllerTimedCommand(m_driverController, 2)));

        m_driverController.a().whileTrue(
            m_intakeSubsystem.createMoveIntakeOutCommand()
                .alongWith(m_shooterSubsystem.createSetRPMCommand(-400)));

        /////////////////////////////
        // Maybe use camera stuff(:D)
        /////////////////////////////

        //drive to amp/side subwoofer
        Command xButtonWithCamera = m_combinedCommands.autoScoreInAmp(m_driverController);
        Command xButtonNoCamera = m_combinedCommands.prepareSpeakerShot(ArmPivotSubsystem.SIDE_SUBWOOFER_ANGLE)
            .alongWith(m_combinedCommands.vibrateIfReadyToShoot(m_driverController));

        Command yButtonWithCamera =  m_chassisSubsystem.createDriveToNoteCommand()
            .alongWith(m_combinedCommands.intakePieceCommand())
            .andThen(new VibrateControllerTimedCommand(m_driverController, 2));
        Command yButtonNoCamera = Commands.none();

        //shoot into speaker
        Command bButtonWithCamera = SpeakerAimAndShootCommand.createShootWhileStationary(m_armPivotSubsystem, m_chassisSubsystem, m_intakeSubsystem, m_shooterSubsystem, 100);
        // Command bButtonWithCamera = CombinedCommands.prepareSpeakerShot(m_armPivotSubsystem, m_shooterSubsystem, m_chassisSubsystem::getPose);
        Command bButtonNoCamera = m_combinedCommands.prepareSpeakerShot(ArmPivotSubsystem.MIDDLE_SUBWOOFER_ANGLE)
            .alongWith(m_combinedCommands.vibrateIfReadyToShoot(m_driverController));
        //feed into
        Command rbButtonWithCamera = new FeedPiecesWithVision(m_driverController, m_chassisSubsystem, m_armPivotSubsystem, m_shooterSubsystem, m_intakeSubsystem);
        Command rbButtonNoCamera = m_combinedCommands.feedPieceAcrossFieldNoVision(m_driverController);



        //Auto-score in amp
        m_driverController.x().whileTrue(Commands.either(xButtonWithCamera, xButtonNoCamera, this::visionEnabled));
        m_driverController.y().whileTrue(Commands.either(yButtonWithCamera, yButtonNoCamera, this::visionEnabled));
        m_driverController.b().whileTrue(Commands.either(bButtonWithCamera, bButtonNoCamera, this::visionEnabled));
        m_driverController.rightBumper().whileTrue(Commands.either(rbButtonWithCamera, rbButtonNoCamera, this::visionEnabled));

        /////////////////////////////
        // Operator Controller
        /////////////////////////////

        //Arm pivot joystick default
        m_armPivotSubsystem.setDefaultCommand(new ArmPivotJoystickCommand(m_armPivotSubsystem, m_operatorController));

        // Intake
        m_operatorController.leftBumper().whileTrue(m_intakeSubsystem.createIntakeUntilPieceCommand());
        m_operatorController.leftTrigger().whileTrue(m_intakeSubsystem.createMoveIntakeInCommand());
        m_operatorController.rightBumper().whileTrue(m_intakeSubsystem.createMoveIntakeOutCommand());

        // shooter
        m_operatorController.rightTrigger().whileTrue(m_shooterSubsystem.createRunSpeakerShotRPMCommand());

        //hanger
        if (HAS_HANGER) {
            m_operatorController.povUp().whileTrue(m_armPivotSubsystem.createMoveArmToPrepHangerAngleCommand());
            m_operatorController.y().whileTrue(m_combinedCommands.prepHangingUp(m_driverController));
            m_operatorController.a().whileTrue(m_hangerSubsystem.createHangerDown());

            m_operatorController.x().and(m_operatorController.povUp()).whileTrue(m_hangerSubsystem.createLeftHangerUp());
            m_operatorController.x().and(m_operatorController.povDown()).whileTrue(m_hangerSubsystem.createLeftHangerDown());

            m_operatorController.b().and(m_operatorController.povUp()).whileTrue(m_hangerSubsystem.createRightHangerUp());
            m_operatorController.b().and(m_operatorController.povDown()).whileTrue(m_hangerSubsystem.createRightHangerDown());
        }
    }


    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An example command will be run in autonomous
        System.out.println("Arm synced: " + m_armPivotSubsystem.areArmEncodersGood());

        return m_autonomousFactory.getSelectedCommand();
    }

    private void resetStickyFaults() {
        m_pdh.clearStickyFaults();
        m_chassisSubsystem.clearStickyFaults();
        m_armPivotSubsystem.clearStickyFaults();
        m_intakeSubsystem.clearStickyFaults();
        m_shooterSubsystem.clearStickyFaults();
        if (HAS_HANGER) {
            m_hangerSubsystem.clearStickyFaults();
        }
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
