// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.wpilib.hardware.hal.AllianceStationID;
import org.wpilib.framework.RobotBase;
import org.wpilib.simulation.DriverStationSim;
import org.wpilib.command2.Commands;
import frc.robot.commands.JoystickFieldRelativeDriveCommand;
import frc.robot.commands.MoveElevatorWithJoystickCommand;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.ChassisSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import org.wpilib.command2.Command;
import org.wpilib.command2.button.CommandNiDsXboxController;
import org.wpilib.command2.button.Trigger;
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
    private final ChassisSubsystem m_chassis;
    private final SuperStructureViz m_superStructureViz; // NOPMD(UnusedPrivateField)


    // Replace with CommandPS4Controller or CommandJoystick if needed
    private final CommandNiDsXboxController m_driverController =
        new CommandNiDsXboxController(Constants.DRIVER_CONTROLLER_PORT);
    private final CommandNiDsXboxController m_joystickController =
        new CommandNiDsXboxController(Constants.JOYSTICK_CONTROLLER_PORT);

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the trigger bindings
        m_chassis = TunerConstants.createDrivetrain();
        m_outtakeSubsystem = new OuttakeSubsystem();
        m_elevatorSubsystem = new ElevatorSubsystem();
        m_superStructureViz = new SuperStructureViz(m_elevatorSubsystem, m_chassis);

        m_chassis.addChassisDebugCommands();
        m_outtakeSubsystem.addOuttakeDebugCommands();
        m_elevatorSubsystem.addElevatorDebugCommands(Constants.IS_COMPETITION_ROBOT);

        configureBindings();
        if (RobotBase.isSimulation()) {
            DriverStationSim.setAllianceStationId(AllianceStationID.BLUE_1);
            DriverStationSim.setDsAttached(true);
            DriverStationSim.setEnabled(true);

        }
    }

    /**
     * Use this method to define your trigger->command mappings. Triggers can be created via the
     * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
     * predicate, or via the named factories in {@link
     * org.wpilib.command2.button.CommandGenericHID}'s subclasses for {@link
     * CommandNiDsXboxController Xbox}/{@link org.wpilib.command2.button.CommandNiDsPS4Controller
     * PS4} controllers or {@link org.wpilib.command2.button.CommandJoystick Flight
     * joysticks}.
     */
    private void configureBindings() {
        m_chassis.setDefaultCommand(new JoystickFieldRelativeDriveCommand(m_chassis, m_driverController));
        m_elevatorSubsystem.setDefaultCommand(new MoveElevatorWithJoystickCommand(m_elevatorSubsystem, m_joystickController));
        m_driverController.start().and(m_driverController.back()).whileTrue(m_chassis.createSeedFieldCentricCommand());
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
