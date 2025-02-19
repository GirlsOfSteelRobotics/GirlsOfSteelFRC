// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.reefscape;

import com.gos.reefscape.commands.Autos;
import com.gos.reefscape.commands.CombinedCommands;
//import com.gos.reefscape.commands.DavidDriveCommand;
import com.gos.reefscape.commands.MovePivotWithJoystickCommand;
import com.gos.reefscape.commands.MoveElevatorWithJoystickCommand;
import com.gos.reefscape.enums.PIEAlgae;
import com.gos.reefscape.enums.PIECoral;
import com.gos.reefscape.subsystems.AlgaeSubsystem;
import com.gos.reefscape.subsystems.CoralSubsystem;
import com.gos.reefscape.subsystems.ElevatorSubsystem;
import com.gos.reefscape.subsystems.LEDSubsystem;
import com.gos.reefscape.subsystems.PivotSubsystem;
import com.gos.reefscape.subsystems.SuperStructureViz;
import com.gos.reefscape.subsystems.ChassisSubsystem;
import com.gos.reefscape.subsystems.TunerConstants;
import com.gos.reefscape.subsystems.sysid.ElevatorSysId;
import com.gos.reefscape.subsystems.sysid.PivotSysId;
import com.gos.reefscape.subsystems.sysid.SwerveDriveSysId;
import com.pathplanner.lib.commands.PathfindingCommand;
import edu.wpi.first.hal.AllianceStationID;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.simulation.DriverStationSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import java.util.Set;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    private final ChassisSubsystem m_chassisSubsystem = TunerConstants.createDrivetrain();
    private final ElevatorSubsystem m_elevatorSubsystem = new ElevatorSubsystem();
    private final CoralSubsystem m_coralSubsystem = new CoralSubsystem();
    private final PivotSubsystem m_pivotSubsystem = new PivotSubsystem();
    private final AlgaeSubsystem m_algaeSubsystem = new AlgaeSubsystem();
    private final CombinedCommands m_combinedCommand = new CombinedCommands(m_algaeSubsystem, m_coralSubsystem, m_elevatorSubsystem, m_pivotSubsystem);

    private final ElevatorSysId m_elevatorSysId;
    private final SwerveDriveSysId m_swerveSysId;
    private final PivotSysId m_pivotSysId;

    private final SuperStructureViz m_superStructureViz = new SuperStructureViz(m_elevatorSubsystem, m_pivotSubsystem); // NOPMD(UnusedPrivateField)
    private final LEDSubsystem m_leds; //NOPMD
    private final Autos m_autos;

    // Replace with CommandPS4Controller or CommandJoystick if needed
    private final CommandXboxController m_driverController =
        new CommandXboxController(Constants.DRIVER_CONTROLLER_PORT);
    private final CommandXboxController m_operatorController = new CommandXboxController(Constants.OPERATOR_CONTROLLER_PORT);

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the trigger bindings
        configureBindings();

        PathfindingCommand.warmupCommand().schedule();
        if (RobotBase.isSimulation()) {
            DriverStationSim.setAllianceStationId(AllianceStationID.Blue1);
            DriverStationSim.setDsAttached(true);
            DriverStationSim.setEnabled(true);
            DriverStation.silenceJoystickConnectionWarning(true);
        }
        m_autos = new Autos(m_chassisSubsystem, m_combinedCommand);
        m_elevatorSysId = new ElevatorSysId(m_elevatorSubsystem);
        m_swerveSysId = new SwerveDriveSysId(m_chassisSubsystem);
        m_pivotSysId = new PivotSysId(m_pivotSubsystem);

        addDebugPathsToShuffleBoard();
        addCoralDebugCommands();
        addPivotDebugCommands();
        addElevatorDebugCommands();
        addAlgaeDebugCommands();
        addSysIdDebugDebugTab();
        SmartDashboard.putData("Clear Sticky Faults", Commands.run(this::resetStickyFaults).ignoringDisable(true).withName("Clear Sticky Faults"));
        SmartDashboard.putData("drive to starting position", createDriveChassisToStartingPoseCommand().withName("drive chassis to start position"));


        createMovePIECommand();
        createMoveRobotToPositionCommand();

        m_leds = new LEDSubsystem(m_algaeSubsystem, m_coralSubsystem, m_elevatorSubsystem, m_combinedCommand, m_autos); // NOPMD(UnusedPrivateField)


        // PropertyManager.purgeExtraKeys();

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
    public Command createDriveChassisToStartingPoseCommand() {
        return Commands.defer(() -> {
            Pose2d startingLocation = m_autos.getSelectedAuto().getStartingLocation().m_pose;
            return m_chassisSubsystem.createDriveToPose(startingLocation);
        }, Set.of(m_chassisSubsystem));
    }

    private void configureBindings() {
        // m_chassisSubsystem.setDefaultCommand(new SwerveWithJoystickCommand(m_chassisSubsystem, m_driverController));
        m_chassisSubsystem.setDefaultCommand(new DavidDriveCommand(m_chassisSubsystem, m_driverController));
        m_elevatorSubsystem.setDefaultCommand(new MoveElevatorWithJoystickCommand(m_elevatorSubsystem, m_operatorController));
        m_pivotSubsystem.setDefaultCommand(new MovePivotWithJoystickCommand(m_pivotSubsystem, m_operatorController));

        m_driverController.start().and(m_driverController.back()).whileTrue(m_chassisSubsystem.createResetGyroCommand());

        m_operatorController.a().whileTrue(m_coralSubsystem.createMoveCoralInCommand());
        m_operatorController.y().whileTrue(m_coralSubsystem.createMoveCoralOutCommand());



        m_driverController.povDown().whileTrue(m_chassisSubsystem.createDriveToClosestAlgaeCommand());
        m_driverController.povLeft().whileTrue(m_chassisSubsystem.createDriveToLeftCoral());
        m_driverController.povRight().whileTrue(m_chassisSubsystem.createDriveToRightCoral());

        m_driverController.leftBumper().whileTrue(m_combinedCommand.scoreCoralCommand(PIECoral.L2));
        m_driverController.rightBumper().whileTrue(m_combinedCommand.scoreCoralCommand(PIECoral.L3));
        m_driverController.leftTrigger().whileTrue(m_combinedCommand.scoreCoralCommand(PIECoral.L4));

        // intake stuff
        m_driverController.a().whileTrue(m_coralSubsystem.createMoveCoralInCommand());
        m_driverController.y().whileTrue(m_coralSubsystem.createMoveCoralOutCommand());
    }


    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An example command will be run in autonomous
        return m_autos.getSelectedAuto();
    }

    private void addCoralDebugCommands() {
        ShuffleboardTab debugTab = Shuffleboard.getTab("Coral Debug");
        debugTab.add(m_coralSubsystem.createMoveCoralInCommand());
        debugTab.add(m_coralSubsystem.createMoveCoralOutCommand());
        debugTab.add(m_coralSubsystem.createIntakeUntilCoralCommand());
    }

    private void addAlgaeDebugCommands() {
        ShuffleboardTab debugTab = Shuffleboard.getTab("Algae Debug");
        debugTab.add(m_algaeSubsystem.createMoveAlgaeInCommand());
        debugTab.add(m_algaeSubsystem.createMoveAlgaeOutCommand());
        debugTab.add(m_algaeSubsystem.createIntakeUntilAlgaeCommand());
    }

    private void addPivotDebugCommands() {
        ShuffleboardTab debugTabPivot = Shuffleboard.getTab("arm pivot");
        debugTabPivot.add(m_pivotSubsystem.createMovePivotToAngleCommand(0.0));
        debugTabPivot.add(m_pivotSubsystem.createMovePivotToAngleCommand(-15.0));
        debugTabPivot.add(m_pivotSubsystem.createMovePivotToAngleCommand(-30.0));
        debugTabPivot.add(m_pivotSubsystem.createMovePivotToAngleCommand(-45.0));
        debugTabPivot.add(m_pivotSubsystem.createMovePivotToAngleCommand(-90.0));
        debugTabPivot.add(m_pivotSubsystem.createMovePivotToAngleCommand(-135.0));
        debugTabPivot.add(m_pivotSubsystem.createMovePivotToAngleCommand(-180.0));
        debugTabPivot.add(m_pivotSubsystem.createPivotoCoastModeCommand());
        debugTabPivot.add(m_pivotSubsystem.createResetEncoderCommand().withName("Reset pivot"));
        debugTabPivot.add(m_pivotSubsystem.createPivotToTunableAngleCommand().withName("Pivot to tunable angle"));
    }

    private void addElevatorDebugCommands() {
        ShuffleboardTab debugTab = Shuffleboard.getTab("Elevator");
        debugTab.add(m_elevatorSubsystem.createMoveElevatorToHeightCommand(Units.feetToMeters(1)));
        debugTab.add(m_elevatorSubsystem.createMoveElevatorToHeightCommand(Units.feetToMeters(3)));
        debugTab.add(m_elevatorSubsystem.createMoveElevatorToHeightCommand(Units.feetToMeters(5)));
        debugTab.add(m_elevatorSubsystem.createMoveElevatorToHeightCommand(Units.feetToMeters(0)));
        debugTab.add(m_elevatorSubsystem.createMoveElevatorToHeightCommand(Units.feetToMeters(2)));
        debugTab.add(m_elevatorSubsystem.createMoveElevatorToHeightCommand(Units.feetToMeters(2.5)));
        debugTab.add(m_elevatorSubsystem.createELevatorToTunableHeightCommand().withName("elevator to tunable height"));
        //l3 15.5




        debugTab.add(m_elevatorSubsystem.createResetEncoderCommand().withName("reset encoder omg"));
        debugTab.add(m_elevatorSubsystem.createElevatorToCoastModeCommand().withName("Move elevator to coast"));
    }


    private void addDebugPathsToShuffleBoard() {
        ShuffleboardTab debugPathsTab = Shuffleboard.getTab("Debug Paths");

        debugPathsTab.add(createDebugPathCommand("TestPath_1mpss_01fps"));
        debugPathsTab.add(createDebugPathCommand("TestPath_1mpss_05fps"));
        debugPathsTab.add(createDebugPathCommand("TestPath_1mpss_10fps"));
        debugPathsTab.add(createDebugPathCommand("TestPath_1mpss_13fps"));
        debugPathsTab.add(createDebugPathCommand("TestPath_1mpss_Maxfps"));
        debugPathsTab.add(createDebugPathCommand("TestPath_4mpss_01fps"));
        debugPathsTab.add(createDebugPathCommand("TestPath_4mpss_05fps"));
        debugPathsTab.add(createDebugPathCommand("TestPath_4mpss_10fps"));
        // debugPathsTab.add(createDebugPathCommand("TestPath_4mpss_13fps"));
        // debugPathsTab.add(createDebugPathCommand("TestPath_4mpss_Maxfps"));
        debugPathsTab.add(createDebugPathCommand("TestPath_9mpss_01fps"));
        debugPathsTab.add(createDebugPathCommand("TestPath_9mpss_05fps"));
        // debugPathsTab.add(createDebugPathCommand("TestPath_9mpss_10fps"));
        // debugPathsTab.add(createDebugPathCommand("TestPath_9mpss_13fps"));
        // debugPathsTab.add(createDebugPathCommand("TestPath_9mpss_Maxfps"));
        debugPathsTab.add(createDebugPathCommand("TestPath_Maxmpss_01fps"));
        debugPathsTab.add(createDebugPathCommand("TestPath_Maxmpss_05fps"));
        debugPathsTab.add(createDebugPathCommand("TestPath_Maxmpss_10fps"));
        debugPathsTab.add(createDebugPathCommand("TestPath_Maxmpss_13fps"));
        debugPathsTab.add(createDebugPathCommand("TestPath_Maxmpss_Maxfps"));
        debugPathsTab.add(createDebugPathCommand("TestRotation_020DegPerSec"));
        debugPathsTab.add(createDebugPathCommand("TestRotation_045DegPerSec"));
        debugPathsTab.add(createDebugPathCommand("TestRotation_090DegPerSec"));
        debugPathsTab.add(createDebugPathCommand("TestRotation_180DegPerSec"));
        debugPathsTab.add(createDebugPathCommand("TestRotation_270DegPerSec"));
        debugPathsTab.add(createDebugPathCommand("TestRotation_360DegPerSec"));
        debugPathsTab.add(createDebugPathCommand("TestRotation_MaxDegPerSec"));


        debugPathsTab.add(createDebugPathCommand("AbbyPaneer"));
        debugPathsTab.add(createDebugPathCommand("PjNoodles"));


    }

    private Command createDebugPathCommand(String name) {
        return Commands.sequence(
            Commands.runOnce(() -> m_chassisSubsystem.resetPose(ChoreoUtils.getPathStartingPose(name).getPose())),
            followChoreoPath(name)
        ).withName(name);
    }

    private void createMovePIECommand() {
        ShuffleboardTab debugTab = Shuffleboard.getTab("Combined Commands");
        debugTab.add(m_combinedCommand.scoreCoralCommand(PIECoral.L1).withName("Level One"));
        debugTab.add(m_combinedCommand.scoreCoralCommand(PIECoral.L2).withName("Level Two"));
        debugTab.add(m_combinedCommand.scoreCoralCommand(PIECoral.L3).withName("Level Three"));
        debugTab.add(m_combinedCommand.scoreCoralCommand(PIECoral.L4).withName("Level Four"));
        debugTab.add(m_combinedCommand.scoreAlgaeCommand(PIEAlgae.SCORE_INTO_NET).withName("Score Into net"));
        debugTab.add(m_combinedCommand.scoreAlgaeCommand(PIEAlgae.SCORE_INTO_PROCESSOR).withName("Score into processor"));
        debugTab.add(m_combinedCommand.fetchPieceFromHPStation().withName("human player station"));
        debugTab.add(m_combinedCommand.fetchAlgae(PIEAlgae.FETCH_ALGAE_2).withName("fetch algae two! :)"));
        debugTab.add(m_combinedCommand.fetchAlgae(PIEAlgae.FETCH_ALGAE_3).withName("fetch algae three! :)"));
        debugTab.add(m_combinedCommand.moveElevatorAndPivotToTunablePosition().withName("elevator and pivot to tunable position"));
    }

    private void createMoveRobotToPositionCommand() {
        ShuffleboardTab debugTab = Shuffleboard.getTab("Move Robot To Position");
        debugTab.add(m_chassisSubsystem.createDriveToPose(ChoreoPoses.E).withName("E"));
        debugTab.add(m_chassisSubsystem.createDriveToPose(ChoreoPoses.C).withName("C"));

    }

    private void addSysIdDebugDebugTab() {
        ShuffleboardTab debugTab = Shuffleboard.getTab("Sys Id debug");
        debugTab.add(m_elevatorSysId.createSysidRoutineCommand().withName("Elevator sysid"));
        debugTab.add(m_swerveSysId.createTranslationSysIdCommand().withName("Translation sysid"));
        debugTab.add(m_swerveSysId.createSteerSysIdCommand().withName("Steer sysid"));
        debugTab.add(m_swerveSysId.createRotationSysIdCommand().withName("Rotation sysid"));
        debugTab.add(m_pivotSysId.createSysidRoutineCommand().withName("Pivot sysid"));

    }

    private void resetStickyFaults() {
        m_elevatorSubsystem.clearStickyFaults();
        m_pivotSubsystem.clearStickyFaults();
        m_algaeSubsystem.clearStickyFaults();
        m_chassisSubsystem.clearStickyFaults();
        m_coralSubsystem.clearStickyFaults();
    }

}
