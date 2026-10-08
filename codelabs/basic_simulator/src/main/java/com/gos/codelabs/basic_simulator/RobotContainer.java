// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.codelabs.basic_simulator;

import com.gos.codelabs.basic_simulator.auton_modes.AutonFactory;
import com.gos.codelabs.basic_simulator.subsystems.ChassisSubsystem;
import com.gos.codelabs.basic_simulator.subsystems.ElevatorSubsystem;
import com.gos.codelabs.basic_simulator.subsystems.PunchSubsystem;
import com.gos.codelabs.basic_simulator.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
@SuppressWarnings("PMD.SingularField")
public class RobotContainer implements AutoCloseable {
    // Subsystems
    private final ChassisSubsystem m_chassisSubsystem;
    private final ElevatorSubsystem m_elevatorSubsystem;
    private final PunchSubsystem m_punchSubsystem;
    private final ShooterSubsystem m_shooterSubsystem;

    // Joysticks
    private final CommandXboxController m_driverJoystick;
    private final CommandXboxController m_operatorJoystick;

    private final AutonFactory m_autonFactory;

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        m_chassisSubsystem = new ChassisSubsystem();
        m_elevatorSubsystem = new ElevatorSubsystem();
        m_punchSubsystem = new PunchSubsystem();
        m_shooterSubsystem = new ShooterSubsystem();

        m_driverJoystick = new CommandXboxController(0);
        m_operatorJoystick = new CommandXboxController(1);

        m_autonFactory = new AutonFactory(m_chassisSubsystem, m_elevatorSubsystem, m_punchSubsystem);

        // Configure the button bindings
        configureButtonBindings();
    }

    @Override
    public void close() {
        m_chassisSubsystem.close();
        m_elevatorSubsystem.close();
        m_punchSubsystem.close();
        m_shooterSubsystem.close();
    }

    /**
     * Puts commands on the dashboard for each subsystem, so they can be tested individually.
     */
    public void addDebugCommands() {
        m_chassisSubsystem.addChassisDebugCommands();
        m_elevatorSubsystem.addElevatorDebugCommands();
        m_punchSubsystem.addPunchDebugCommands();
        m_shooterSubsystem.addShooterDebugCommands();
    }

    private void configureButtonBindings() {
        // TODO implement
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return m_autonFactory.getAutonMode();
    }

    public ElevatorSubsystem getElevator() {
        return m_elevatorSubsystem;
    }

    public ChassisSubsystem getChassis() {
        return m_chassisSubsystem;
    }

    public PunchSubsystem getPunch() {
        return m_punchSubsystem;
    }

    public ShooterSubsystem getShooter() {
        return m_shooterSubsystem;
    }
}
