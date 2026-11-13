// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.hal.AllianceStationID;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.simulation.DriverStationSim;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.autos.AutoFactory;
import frc.robot.commands.CombinedCommand;
import frc.robot.commands.JoystickFieldRelativeDriveCommand;
import frc.robot.commands.MoveElevatorWithJoystickCommand;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.ChassisSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.OuttakeSubsystem;
import frc.robot.subsystems.SuperStructureViz;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    private final ElevatorSubsystem m_elevatorSubsystem;
    private final OuttakeSubsystem m_outtakeSubsystem;
    private final ChassisSubsystem m_chassisSubsystem;
    private final SuperStructureViz m_superStructureViz; // NOPMD(UnusedPrivateField)
    private final AutoFactory m_autoFactory; // NOPMD(UnusedPrivateField)
    private final CombinedCommand m_combinedCommand;

    // Replace with CommandPS4Controller or CommandJoystick if needed
    private final CommandXboxController m_driverController =
        new CommandXboxController(Constants.DRIVER_CONTROLLER_PORT);
    private final CommandXboxController m_joystickController =
        new CommandXboxController(Constants.JOYSTICK_CONTROLLER_PORT);

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the trigger bindings
        m_chassisSubsystem = TunerConstants.createDrivetrain();
        m_outtakeSubsystem = new OuttakeSubsystem();
        m_elevatorSubsystem = new ElevatorSubsystem();
        m_superStructureViz = new SuperStructureViz(m_elevatorSubsystem, m_chassisSubsystem);

        m_chassisSubsystem.addChassisDebugCommands();
        m_outtakeSubsystem.addOuttakeDebugCommands();
        m_elevatorSubsystem.addElevatorDebugCommands(Constants.IS_COMPETITION_ROBOT);

        m_combinedCommand = new CombinedCommand(m_chassisSubsystem, m_outtakeSubsystem, m_elevatorSubsystem);
        m_autoFactory = new AutoFactory(m_chassisSubsystem, m_combinedCommand);

        configureBindings();
        if (RobotBase.isSimulation()) {
            DriverStationSim.setAllianceStationId(AllianceStationID.Blue1);
            DriverStationSim.setDsAttached(true);
            DriverStationSim.setEnabled(true);
            DriverStation.silenceJoystickConnectionWarning(true);

        }
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
        m_chassisSubsystem.setDefaultCommand(new JoystickFieldRelativeDriveCommand(m_chassisSubsystem, m_driverController));
        m_elevatorSubsystem.setDefaultCommand(new MoveElevatorWithJoystickCommand(m_elevatorSubsystem, m_joystickController));
        m_driverController.start().and(m_driverController.back()).whileTrue(m_chassisSubsystem.createSeedFieldCentricCommand());
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An example command will be run in autonomous
        return Commands.none();
    }

}
