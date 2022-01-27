// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.rapidreact;

import com.gos.rapidreact.commands.CollectorDownCommand;
import com.gos.rapidreact.commands.CollectorPivotPIDCommand;
import com.gos.rapidreact.commands.CollectorUpCommand;
import com.gos.rapidreact.commands.RollerInCommand;
import com.gos.rapidreact.commands.RollerOutCommand;
import com.gos.rapidreact.commands.TeleopArcadeChassisCommand;
import com.gos.rapidreact.subsystems.ChassisSubsystem;
import com.gos.rapidreact.subsystems.CollectorSubsystem;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...

    private final ChassisSubsystem m_chassis = new ChassisSubsystem();
    private final CollectorSubsystem m_collector = new CollectorSubsystem();

    private final XboxController m_driverJoystick = new XboxController(0);

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();

        SmartDashboard.putData("CollectorDownCommand", new CollectorDownCommand(m_collector));
        SmartDashboard.putData("CollectorUpCommand", new CollectorUpCommand(m_collector));
        SmartDashboard.putData("RollerInCommand", new RollerInCommand(m_collector));
        SmartDashboard.putData("RollerOutCommand", new RollerOutCommand(m_collector));
        SmartDashboard.putData("CollectorPivotPIDCommand - 0 Degrees", new CollectorPivotPIDCommand(m_collector, 0));
        SmartDashboard.putData("CollectorPivotPIDCommand - 45 Degrees", new CollectorPivotPIDCommand(m_collector, Math.toRadians(45)));
        SmartDashboard.putData("CollectorPivotPIDCommand - 90 Degrees", new CollectorPivotPIDCommand(m_collector, Math.toRadians(90)));

    }


    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        m_chassis.setDefaultCommand(new TeleopArcadeChassisCommand(m_chassis, m_driverJoystick));
        // Add button to command mappings here.
        // See https://docs.wpilib.org/en/stable/docs/software/commandbased/binding-commands-to-triggers.html
    }


    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return null;
    }
}
