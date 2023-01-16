// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.preseason2023;

import com.gos.preseason2023.commands.CollectorPivotCommand;
import com.gos.preseason2023.commands.CollectorRollerCommand;
import com.gos.preseason2023.subsystems.CollectorExampleSubsystem;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import com.gos.preseason2023.commands.ExampleCommand;
import com.gos.preseason2023.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

    private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

    private final CollectorExampleSubsystem m_collectorSubsystem = new CollectorExampleSubsystem();

    private final XboxController m_driverController = new XboxController(0);


    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();
    }


    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        // Add button to command mappings here.
        // See https://docs.wpilib.org/en/stable/docs/software/commandbased/binding-commands-to-triggers.html

        final JoystickButton pivotUp = new JoystickButton(m_driverController, XboxController.Button.kRightBumper.value);
        pivotUp.whileTrue(new CollectorPivotCommand(m_collectorSubsystem, 0.5));
        final JoystickButton pivotDown = new JoystickButton(m_driverController, XboxController.Button.kLeftBumper.value);
        pivotDown.whileTrue(new CollectorPivotCommand(m_collectorSubsystem, -0.5));
        final JoystickButton rollerIn = new JoystickButton(m_driverController, XboxController.Button.kA.value);
        rollerIn.whileTrue(new CollectorRollerCommand(m_collectorSubsystem, 0.5));
        final JoystickButton rollerOut = new JoystickButton(m_driverController, XboxController.Button.kX.value);
        rollerOut.whileTrue(new CollectorRollerCommand(m_collectorSubsystem, -0.5));
    }


    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return m_autoCommand;
    }
}
