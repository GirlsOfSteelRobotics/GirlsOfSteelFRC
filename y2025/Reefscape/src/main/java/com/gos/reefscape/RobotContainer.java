// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.reefscape;

import com.gos.reefscape.commands.Autos;
import com.gos.reefscape.commands.CombinedCommands;
import com.gos.reefscape.commands.DavidDriveCommand;
import com.gos.reefscape.commands.MovePivotWithJoystickCommand;
import com.gos.reefscape.commands.MoveElevatorWithJoystickCommand;
import com.gos.reefscape.subsystems.AlgaeSubsystem;
import com.gos.reefscape.subsystems.CoralSubsystem;
import com.gos.reefscape.subsystems.ElevatorSubsystem;
import com.gos.reefscape.subsystems.LEDSubsystem;
import com.gos.reefscape.subsystems.PivotSubsystem;
import com.gos.reefscape.subsystems.SuperStructureViz;
import com.gos.reefscape.subsystems.ChassisSubsystem;
import com.gos.reefscape.subsystems.TunerConstants;
import com.pathplanner.lib.commands.PathfindingCommand;
import edu.wpi.first.hal.AllianceStationID;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.simulation.DriverStationSim;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

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

    private final SuperStructureViz m_superStructureViz = new SuperStructureViz(m_elevatorSubsystem, m_pivotSubsystem); // NOPMD(UnusedPrivateField)
    private final LEDSubsystem m_leds = new LEDSubsystem(m_algaeSubsystem, m_coralSubsystem, m_elevatorSubsystem, m_combinedCommand); // NOPMD(UnusedPrivateField)

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
        addDebugPathsToShuffleBoard();
        addCoralDebugCommands();
        addPivotDebugCommands();
        addElevatorDebugCommands();
        addAlgaeDebugCommands();

        createMovePIECommand();
        createMoveRobotToPositionCommand();

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
    private void configureBindings() {
        m_chassisSubsystem.setDefaultCommand(new DavidDriveCommand(m_chassisSubsystem, m_driverController));
        m_elevatorSubsystem.setDefaultCommand(new MoveElevatorWithJoystickCommand(m_elevatorSubsystem, m_operatorController));
        m_pivotSubsystem.setDefaultCommand(new MovePivotWithJoystickCommand(m_pivotSubsystem, m_operatorController));
        m_driverController.b().whileTrue(m_chassisSubsystem.createDriveToPose(ChoreoPoses.C));
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
        debugTabPivot.add(m_pivotSubsystem.createMoveArmtoAngleCommand(0.0));
        debugTabPivot.add(m_pivotSubsystem.createMoveArmtoAngleCommand(45.0));
        debugTabPivot.add(m_pivotSubsystem.createMoveArmtoAngleCommand(90.0));

    }

    private void addElevatorDebugCommands() {
        ShuffleboardTab debugTab = Shuffleboard.getTab("Elevator");
        debugTab.add(m_elevatorSubsystem.createMoveElevatorToHeightCommand(Units.feetToMeters(1)));
        debugTab.add(m_elevatorSubsystem.createMoveElevatorToHeightCommand(Units.feetToMeters(3)));
        debugTab.add(m_elevatorSubsystem.createMoveElevatorToHeightCommand(Units.feetToMeters(5)));
    }


    private void addDebugPathsToShuffleBoard() {
        ShuffleboardTab debugPathsTab = Shuffleboard.getTab("Debug Paths");

        debugPathsTab.add(createDebugPathCommand("TestPath_1mpss_01fps"));
        debugPathsTab.add(createDebugPathCommand("TestPath_1mpss_05fps"));
        debugPathsTab.add(createDebugPathCommand("TestPath_1mpss_10fps"));
        debugPathsTab.add(createDebugPathCommand("TestPath_1mpss_13fps"));
        debugPathsTab.add(createDebugPathCommand("TestPath_4mpss_01fps"));
        debugPathsTab.add(createDebugPathCommand("TestPath_4mpss_05fps"));
        // debugPathsTab.add(createDebugPathCommand("TestPath_4mpss_10fps"));
        // debugPathsTab.add(createDebugPathCommand("TestPath_4mpss_13fps"));
        // debugPathsTab.add(createDebugPathCommand("TestPath_4mpss_Maxfps"));
        debugPathsTab.add(createDebugPathCommand("TestPath_9mpss_01fps"));
        // debugPathsTab.add(createDebugPathCommand("TestPath_9mpss_05fps"));
        // debugPathsTab.add(createDebugPathCommand("TestPath_9mpss_10fps"));
        // debugPathsTab.add(createDebugPathCommand("TestPath_9mpss_13fps"));
        // debugPathsTab.add(createDebugPathCommand("TestPath_9mpss_Maxfps"));

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
        debugTab.add(m_combinedCommand.scoreCoralCommand(PIE.L1).withName("Level One"));
        debugTab.add(m_combinedCommand.scoreCoralCommand(PIE.L2).withName("Level Two"));
        debugTab.add(m_combinedCommand.scoreCoralCommand(PIE.L3).withName("Level Three"));
        debugTab.add(m_combinedCommand.scoreCoralCommand(PIE.L4).withName("Level Four"));
        debugTab.add(m_combinedCommand.scoreCoralCommand(PIE.SCORE_INTO_NET).withName("Score Into net"));
        debugTab.add(m_combinedCommand.scoreCoralCommand(PIE.SCORE_INTO_PROCESSOR).withName("Score into processor"));
        debugTab.add(m_combinedCommand.fetchPieceFromHPStation().withName("human player station"));
        debugTab.add(m_combinedCommand.fetchAlgaeTwo().withName("Fetch Algae"));

    }

    private void createMoveRobotToPositionCommand() {
        ShuffleboardTab debugTab = Shuffleboard.getTab("Move Robot To Position");
        debugTab.add(m_chassisSubsystem.createDriveToPose(ChoreoPoses.E).withName("E"));
        debugTab.add(m_chassisSubsystem.createDriveToPose(ChoreoPoses.C).withName("C"));

    }

}
