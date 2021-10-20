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

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
@SuppressWarnings("PMD.SingularField")
public class RobotContainer {
    // Subsystems
    private final ChassisSubsystem m_chassisSubsystem;
    private final ElevatorSubsystem m_elevatorSubsystem;
    private final PunchSubsystem m_punchSubsystem;
    private final ShooterSubsystem m_shooterSubsystem;

    private final AutonFactory m_autonFactory;

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        m_chassisSubsystem = new ChassisSubsystem();
        m_elevatorSubsystem = new ElevatorSubsystem();
        m_punchSubsystem = new PunchSubsystem();
        m_shooterSubsystem = new ShooterSubsystem();

        new OI(this);
        new CommandTester(this);
        m_autonFactory = new AutonFactory(this);
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
