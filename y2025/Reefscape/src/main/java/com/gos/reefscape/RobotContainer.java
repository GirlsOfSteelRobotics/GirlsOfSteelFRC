// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.reefscape;

import com.gos.lib.joysticks.VibrateControllerTimedCommand;
import com.gos.lib.joysticks.VibrateControllerWhileTrueCommand;
import com.gos.lib.properties.PropertyManager;
import com.gos.reefscape.auto.modes.GosAuto;
import com.gos.reefscape.commands.Autos;
import com.gos.reefscape.commands.CombinedCommands;
import com.gos.reefscape.commands.SwerveWithJoystickCommand;
import com.gos.reefscape.commands.MovePivotWithJoystickCommand;
import com.gos.reefscape.commands.MoveElevatorWithJoystickCommand;
import com.gos.reefscape.commands.RobotRelativeDriveCommand;
import com.gos.reefscape.enums.KeepOutZoneEnum;
import com.gos.reefscape.enums.PIEAlgae;
import com.gos.reefscape.enums.PIECoral;
import com.gos.reefscape.generated.DebugPathsTab;
import com.gos.reefscape.subsystems.CoralSubsystem;
import com.gos.reefscape.subsystems.ElevatorSubsystem;
import com.gos.reefscape.subsystems.LEDSubsystem;
import com.gos.reefscape.subsystems.PivotSubsystem;
import com.gos.reefscape.subsystems.SuperStructureViz;
import com.gos.reefscape.subsystems.ChassisSubsystem;
import com.gos.reefscape.subsystems.OperatorCoralCommand;
import edu.wpi.first.wpilibj.simulation.DIOSim;
import edu.wpi.first.wpilibj2.command.DeferredCommand;
import frc.robot.generated.TunerConstantsCompetition;
import com.gos.reefscape.subsystems.sysid.ElevatorSysId;
import com.gos.reefscape.subsystems.sysid.PivotSysId;
import com.gos.reefscape.subsystems.sysid.SwerveDriveSysId;
import com.pathplanner.lib.commands.PathfindingCommand;
import edu.wpi.first.hal.AllianceStationID;
import edu.wpi.first.math.geometry.Pose2d;
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
import java.util.function.Consumer;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    private final ChassisSubsystem m_chassisSubsystem = TunerConstantsCompetition.createDrivetrain();
    private final ElevatorSubsystem m_elevatorSubsystem = new ElevatorSubsystem();
    private final CoralSubsystem m_coralSubsystem = new CoralSubsystem();
    private final PivotSubsystem m_pivotSubsystem = new PivotSubsystem();
    private final CombinedCommands m_combinedCommand;
    private final OperatorCoralCommand m_operatorCoralCommand = new OperatorCoralCommand();

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
        Consumer<KeepOutZoneEnum> keepOutConsumer = this::handleKeepOutZoneState;
        m_combinedCommand = new CombinedCommands(m_coralSubsystem, m_elevatorSubsystem, m_pivotSubsystem, keepOutConsumer);

        // Configure the trigger bindings
        configureBindings();

        PathfindingCommand.warmupCommand().schedule();
        if (RobotBase.isSimulation()) {
            DriverStationSim.setAllianceStationId(AllianceStationID.Blue1);
            DriverStationSim.setDsAttached(true);
            DriverStationSim.setEnabled(true);
            DriverStation.silenceJoystickConnectionWarning(true);

            new DIOSim(Constants.CORAL_SENSOR_ID).setValue(false);
        }
        m_autos = new Autos(m_chassisSubsystem, m_combinedCommand);
        m_elevatorSysId = new ElevatorSysId(m_elevatorSubsystem);
        m_swerveSysId = new SwerveDriveSysId(m_chassisSubsystem);
        m_pivotSysId = new PivotSysId(m_pivotSubsystem);

        boolean inComp = false;
        m_coralSubsystem.addCoralDebugCommands(inComp);
        m_pivotSubsystem.addPivotDebugCommands(inComp);
        m_elevatorSubsystem.addElevatorDebugCommands(inComp);
        m_chassisSubsystem.addChassisDebugCommands(inComp);
        m_combinedCommand.createCombinedCommand(inComp);
        m_operatorCoralCommand.tellCoralPosition();
        if (!inComp) {
            addSysIdDebugDebugTab();
            new DebugPathsTab(m_chassisSubsystem).addDebugPathsToShuffleBoard();
        }

        SmartDashboard.putData("Clear Sticky Faults", Commands.run(this::resetStickyFaults).ignoringDisable(true).withName("Clear Sticky Faults"));
        SmartDashboard.putData("drive to starting position", createDriveChassisToStartingPoseCommand().withName("drive chassis to start position"));



        m_leds = new LEDSubsystem(m_coralSubsystem, m_elevatorSubsystem, m_autos); // NOPMD(UnusedPrivateField)

        if (RobotBase.isReal()) {
            PropertyManager.printDynamicProperties(false);
        }

        // PropertyManager.purgeExtraKeys();

        keepOutConsumer.accept(KeepOutZoneEnum.NOT_RUNNING);

    }

    private void handleKeepOutZoneState(KeepOutZoneEnum state) {
        m_leds.setKeepOutZoneState(state);
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
        ///////////////////////////
        // Default Commands
        ///////////////////////////
        m_chassisSubsystem.setDefaultCommand(new SwerveWithJoystickCommand(m_chassisSubsystem, m_driverController));
        // m_chassisSubsystem.setDefaultCommand(new DavidDriveCommand(m_chassisSubsystem, m_driverController));
        m_elevatorSubsystem.setDefaultCommand(new MoveElevatorWithJoystickCommand(m_elevatorSubsystem, m_operatorController));
        m_pivotSubsystem.setDefaultCommand(new MovePivotWithJoystickCommand(m_pivotSubsystem, m_operatorController));


        ///////////////////////////
        // Driver controller
        ///////////////////////////
        m_driverController.start().and(m_driverController.back()).whileTrue(m_chassisSubsystem.createResetGyroCommand());

        // Drive to position
        m_driverController.povDown().whileTrue(m_chassisSubsystem.createDriveToClosestAlgaeCommand().andThen(new RobotRelativeDriveCommand(m_chassisSubsystem, m_driverController)));
        m_driverController.povLeft().whileTrue(m_chassisSubsystem.createDriveToLeftCoral().andThen(new RobotRelativeDriveCommand(m_chassisSubsystem, m_driverController)));
        m_driverController.povRight().whileTrue(m_chassisSubsystem.createDriveToRightCoral().andThen(new RobotRelativeDriveCommand(m_chassisSubsystem, m_driverController)));
        // m_driverController.povRight().whileTrue(new RobotRelativeDriveCommand(m_chassisSubsystem, m_driverController));

        Trigger fetchAlgaeTrigger = m_driverController.rightBumper();
        Trigger prepAlgaeTrigger = m_driverController.rightTrigger().and(m_driverController.a().negate());
        Trigger scoreAlgaeTrigger = m_driverController.rightTrigger().and(m_driverController.a());

        Trigger fetchCoralTrigger = m_driverController.leftBumper();
        Trigger prepCoralTrigger = m_driverController.leftTrigger().and(m_driverController.a().negate());
        Trigger scoreCoralTrigger = m_driverController.leftTrigger().and(m_driverController.a());


        // Buttons
        // .a() is used for scoring game pieces above
        m_driverController.y().whileTrue(m_coralSubsystem.createReverseIntakeCommand());
        m_driverController.b().whileTrue(m_combinedCommand.goHome());
        m_driverController.x().whileTrue(m_combinedCommand.scoreAlgaeInNet());


        fetchCoralTrigger.whileTrue(m_combinedCommand.fetchPieceFromHPStation()
            .andThen(new VibrateControllerTimedCommand(m_driverController, 2)));


        fetchAlgaeTrigger.whileTrue(new DeferredCommand(() -> {
            PIEAlgae mAlgaeHeight = m_chassisSubsystem.findClosestAlgae().m_algaeHeight;
            return m_combinedCommand.fetchAlgae(mAlgaeHeight)
                .alongWith(new VibrateControllerWhileTrueCommand(m_driverController, m_coralSubsystem::hasAlgae));
        }, Set.of(m_elevatorSubsystem, m_pivotSubsystem)));

        prepAlgaeTrigger.whileTrue(m_combinedCommand.prepAlgaeInProcessorCommand());
        scoreAlgaeTrigger.whileTrue(m_coralSubsystem.createMoveAlgaeOutCommand());


        prepCoralTrigger.whileTrue(new DeferredCommand(() -> {
            PIECoral setpoint = m_operatorCoralCommand.getSetpoint();
            return m_combinedCommand.prepScoreCoralCommand(setpoint)
                .alongWith(new VibrateControllerWhileTrueCommand(m_driverController, m_combinedCommand::isAtGoalHeightAngle));
        }, Set.of(m_elevatorSubsystem, m_pivotSubsystem)));

        scoreCoralTrigger.whileTrue(m_coralSubsystem.createScoreCoralCommand());

        ///////////////////////////
        // Operator controller
        ///////////////////////////
        m_operatorController.a().whileTrue(m_coralSubsystem.createReverseIntakeCommand());
        m_operatorController.y().whileTrue(m_coralSubsystem.createScoreCoralCommand());
        m_operatorController.x().whileTrue(m_coralSubsystem.createScoreCoralCommand());

        m_operatorController.b().whileTrue(m_coralSubsystem.createIntakeUntilCoralCommand());

        //operator coral commands
        m_operatorController.povUp().whileTrue(m_operatorCoralCommand.changeCoralPosition(PIECoral.L4));
        m_operatorController.povDown().whileTrue(m_operatorCoralCommand.changeCoralPosition(PIECoral.L1));
        m_operatorController.povLeft().whileTrue(m_operatorCoralCommand.changeCoralPosition(PIECoral.L2));
        m_operatorController.povRight().whileTrue(m_operatorCoralCommand.changeCoralPosition(PIECoral.L3));
    }



    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An example command will be run in autonomous
        GosAuto mode = m_autos.getSelectedAuto();
        if (mode != null) {
            System.out.println("Running '" + mode.getName() + "' auto");
        }
        return mode;
    }



    private Command createDriveChassisToStartingPoseCommand() {
        return Commands.defer(() -> {
            Pose2d startingLocation = m_autos.getSelectedAuto().getStartingLocation().m_pose.getPose();
            return m_chassisSubsystem.createDriveToPose(startingLocation);
        }, Set.of(m_chassisSubsystem));
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
        m_chassisSubsystem.clearStickyFaults();
        m_coralSubsystem.clearStickyFaults();
    }

}
