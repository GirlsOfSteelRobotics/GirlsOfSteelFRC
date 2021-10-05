/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                                                */
/* Open Source Software - may be modified and shared by FRC teams. The code     */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                                                                                             */
/*----------------------------------------------------------------------------*/

package com.gos.testboard2020;

import com.gos.testboard2020.commands.OuterShootAlign2;
import com.gos.testboard2020.commands.ReadLidar;
import com.gos.testboard2020.commands.SpinByLidar;
import com.gos.testboard2020.commands.SwitchToCamClimb;
import com.gos.testboard2020.commands.SwitchToCamIntake;
import com.gos.testboard2020.commands.TryBlinkin;
import com.gos.testboard2020.subsystems.Blinkin;
import com.gos.testboard2020.subsystems.Camera;
import com.gos.testboard2020.subsystems.Lidar;
import com.gos.testboard2020.subsystems.Limelight;
import com.gos.testboard2020.subsystems.Motor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
@SuppressWarnings("PMD.SingularField")
public class RobotContainer {

    // The robot's subsystems are declared here
    private final Blinkin m_blinkin;
    private final Camera m_camera;
    private final Lidar m_lidar;
    private final Motor m_motor;
    private final Limelight m_limelight;
    private final SendableChooser<Command> m_sendableChooser;
    private Joystick drivingPad;

    /**
     * The container for the robot. Contains subsystems and the OI (joystick/gamepad) object.
     */
    public RobotContainer() {

        // Create all subsystems in this section:
        m_blinkin = new Blinkin ();
        m_camera = new Camera ();
        m_lidar = new Lidar();
        m_motor = new Motor();
        m_limelight = new Limelight();

        // Configure the button bindings
        configureButtonBindings();

        // Add some testing options to the Autonomous Chooser on the dashboard
        m_sendableChooser = new SendableChooser<>();
        m_sendableChooser.addOption("Test SwitchToCamClimb", new SwitchToCamClimb(m_camera));
        m_sendableChooser.addOption("Test SwitchtoCamIntake", new SwitchToCamClimb(m_camera));
        SmartDashboard.putData("Auto Mode", m_sendableChooser);
    }

    /**
     * Use this method to define your button->command mappings.  Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
     * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        drivingPad = new Joystick(0);

        new JoystickButton(drivingPad, 1).	whenPressed(new TryBlinkin(m_blinkin));
        new JoystickButton(drivingPad, 2).	whenPressed(new ReadLidar(m_lidar));
        new JoystickButton(drivingPad, 4).	whenPressed(new SpinByLidar(m_motor, m_lidar));

        new JoystickButton(drivingPad, 3).whenPressed(new OuterShootAlign2(m_limelight));

        new POVButton(drivingPad, 0).		whenPressed(new SwitchToCamClimb(m_camera));
        new POVButton(drivingPad, 180).		whenPressed(new SwitchToCamIntake(m_camera));
    }

  /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        SequentialCommandGroup group = new SequentialCommandGroup();
        group.addCommands(new ReadLidar(m_lidar));
        if (m_sendableChooser.getSelected() != null) {
            group.addCommands(m_sendableChooser.getSelected());
        }
        return group;
    }
}
