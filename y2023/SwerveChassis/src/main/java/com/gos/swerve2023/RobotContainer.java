// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.swerve2023;

import com.gos.swerve2023.commands.ChassisTeleopDriveCommand;
import com.gos.swerve2023.subsystems.ChassisSubsystem;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.hal.AllianceStationID;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.simulation.DriverStationSim;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
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
    private final SendableChooser<Command> m_autoChooser;

    public RobotContainer() {
        m_chassis = new ChassisSubsystem();

        m_driverJoystick = new CommandXboxController(0);

        // Configure the trigger bindings
        configureBindings();

        ShuffleboardTab tab = Shuffleboard.getTab("SwerveTest");
        tab.add(m_chassis.createTestSingleModleCommand(0));
        tab.add(m_chassis.createTestSingleModleCommand(1));
        tab.add(m_chassis.createTestSingleModleCommand(2));
        tab.add(m_chassis.createTestSingleModleCommand(3));

        tab.add("Chassis Set Pose Subwoofer Mid", m_chassis.createResetPoseCommand(new Pose2d(1.34, 5.55, Rotation2d.fromDegrees(0))).withName("Reset Pose Subwoofer Mid"));

        NamedCommands.registerCommand("AimAndShootIntoSpeaker", new WaitCommand(1));

        m_autoChooser = AutoBuilder.buildAutoChooser();
        SmartDashboard.putData(m_autoChooser);

        if (RobotBase.isSimulation()) {
            DriverStationSim.setEnabled(true);
            DriverStationSim.setAllianceStationId(AllianceStationID.Blue1);
        }

        // PropertyManager.purgeExtraKeys();
    }


    /**
     * Use this method to define your trigger->command mappings.
     */
    private void configureBindings() {
        m_chassis.setDefaultCommand(new ChassisTeleopDriveCommand(m_chassis, m_driverJoystick));
        m_driverJoystick.start().and(m_driverJoystick.back())
            .whileTrue(m_chassis.createResetGyroCommand());
    }


    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return m_autoChooser.getSelected();
    }
}
