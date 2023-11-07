// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.swerve2023;

import com.gos.swerve2023.commands.ChassisTeleopDriveCommand;
import com.gos.swerve2023.subsystems.ChassisSubsystem;
import edu.wpi.first.hal.AllianceStationID;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.simulation.DriverStationSim;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
    private final ChassisSubsystem m_chassis;
    private final CommandXboxController m_driverJoystick;

    public RobotContainer() {
        m_chassis = new ChassisSubsystem();

        m_driverJoystick = new CommandXboxController(0);

        // Configure the trigger bindings
        configureBindings();

        if (RobotBase.isSimulation()) {
            DriverStationSim.setEnabled(true);
            DriverStationSim.setAllianceStationId(AllianceStationID.Blue1);
        }
    }


    /**
     * Use this method to define your trigger->command mappings.
     */
    private void configureBindings() {
        m_chassis.setDefaultCommand(new ChassisTeleopDriveCommand(m_chassis, m_driverJoystick));
    }


    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // TODO: Implement properly
        return null;
    }
}
