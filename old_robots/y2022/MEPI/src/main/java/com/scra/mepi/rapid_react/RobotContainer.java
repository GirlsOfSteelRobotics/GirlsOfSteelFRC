// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.scra.mepi.rapid_react;

import com.scra.mepi.rapid_react.autos.AutonomousFactory;
import com.scra.mepi.rapid_react.commands.ClimberCommand;
import com.scra.mepi.rapid_react.commands.DriveCommand;
import com.scra.mepi.rapid_react.commands.HopperCommand;
import com.scra.mepi.rapid_react.commands.KickIfShootSetRPMCommand;
import com.scra.mepi.rapid_react.commands.RunIntakeCommand;
import com.scra.mepi.rapid_react.commands.SetShooterSpeedCommand;
import com.scra.mepi.rapid_react.commands.ShootFromDistanceCommand;
import com.scra.mepi.rapid_react.commands.StopIntakeCommand;
import com.scra.mepi.rapid_react.commands.TowerDownCommand;
import com.scra.mepi.rapid_react.commands.TowerUpCommand;
import com.scra.mepi.rapid_react.subsystems.ClimberSubsystem;
import com.scra.mepi.rapid_react.subsystems.DrivetrainSubsystem;
import com.scra.mepi.rapid_react.subsystems.HopperSubsystem;
import com.scra.mepi.rapid_react.subsystems.IntakeSubsystem;
import com.scra.mepi.rapid_react.subsystems.LimelightSubsystem;
import com.scra.mepi.rapid_react.subsystems.ShooterSubsytem;
import com.scra.mepi.rapid_react.subsystems.TowerSubsystem;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
@SuppressWarnings("PMD.TooManyFields")
public class RobotContainer {
    private final CommandXboxController m_driveJoystick = new CommandXboxController(0);
    private final CommandXboxController m_operatorJoystick = new CommandXboxController(1);

    // The robot's subsystems and commands are defined here...
    private final IntakeSubsystem m_intakeSubsystem = new IntakeSubsystem();
    private final DrivetrainSubsystem m_drivetrainSubsystem = new DrivetrainSubsystem();
    private final ClimberSubsystem m_climberSubsystem = new ClimberSubsystem();
    private final ShooterSubsytem m_shooterSubsystem = new ShooterSubsytem();
    private final TowerSubsystem m_towerSubsystem = new TowerSubsystem();
    private final HopperSubsystem m_hopperSubsystem = new HopperSubsystem();

    private final LimelightSubsystem m_limelightSubsystem = new LimelightSubsystem();

    private final ShooterLookupTable m_shooterLookupTable = new ShooterLookupTable();

    private final AutonomousFactory m_autonomousFactory =
            new AutonomousFactory(m_shooterSubsystem);

    public RobotContainer() {
        CameraServer.startAutomaticCapture();
        // Configure the button bindings
        configureButtonBindings();
        m_intakeSubsystem.setDefaultCommand(new StopIntakeCommand(m_intakeSubsystem, m_hopperSubsystem));
        m_hopperSubsystem.setDefaultCommand(new HopperCommand(m_hopperSubsystem, 0));
        m_shooterSubsystem.setDefaultCommand(new SetShooterSpeedCommand(m_shooterSubsystem, 0));
        m_drivetrainSubsystem.setDefaultCommand(new DriveCommand(m_drivetrainSubsystem, m_driveJoystick));
        m_climberSubsystem.setDefaultCommand(new ClimberCommand(m_climberSubsystem, 0));
        //        ShuffleboardTab testCommands = Shuffleboard.getTab("test commands");

        // testCommands.add("ClimbUp", new ClimberCommand(m_climberSubsystem, 0.25));
        // testCommands.add("ClimbDown", new ClimberCommand(m_climberSubsystem, -0.25));
        // testCommands.add("IntakeIn", new RunIntakeCommand(m_intakeSubsystem, 0.75,
        // m_hopperSubsystem));
        // testCommands.add(
        //         "IntakeOut", new RunIntakeCommand(m_intakeSubsystem, -0.75, m_hopperSubsystem));
        // testCommands.add(
        //         "Shoot RPM", new ShooterPIDCommand(m_shooterSubsystem, m_tunableShooterGoal.get()));
        // testCommands.add("Tower Up", new TowerUpCommand(m_towerSubsystem));
        // testCommands.add("Tower Down", new TowerDownCommand(m_towerSubsystem));
        // testCommands.add("Tower Kicker", new TowerKickerCommand(m_towerSubsystem));
        // testCommands.add("Hopper Up", new HopperCommand(m_hopperSubsystem, 0.5));
        // testCommands.add("Hopper Down", new HopperCommand(m_hopperSubsystem, -0.5));
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        /* FOR SIENNA :)
        moving drivetrain
        intake out/in
        shooter + kicker logic
        conveyor up/down
        climber up/down
        kicker separately
        */

        // driver joystick

        final RunIntakeCommand intakeInCommand =
            new RunIntakeCommand(m_intakeSubsystem, 0.5, m_hopperSubsystem, m_towerSubsystem, 0.5);
        final RunIntakeCommand intakeOutCommand =
            new RunIntakeCommand(m_intakeSubsystem, -0.5, m_hopperSubsystem, m_towerSubsystem, -0.5);

        m_driveJoystick.rightTrigger().whileTrue(intakeInCommand);
        m_driveJoystick.rightBumper().whileTrue(intakeOutCommand);

        m_driveJoystick.leftBumper().whileTrue(new ClimberCommand(m_climberSubsystem, 0.75));
        m_driveJoystick.leftTrigger().whileTrue(new ClimberCommand(m_climberSubsystem, -0.75));

        m_driveJoystick.b().whileTrue(new KickIfShootSetRPMCommand(m_shooterSubsystem, m_towerSubsystem, 1000));
        m_driveJoystick.a()
            .whileTrue(new KickIfShootSetRPMCommand(m_shooterSubsystem, m_towerSubsystem, 1200));

        // testing: individually shoot out by addressing shooter and tower
        m_operatorJoystick.x().whileTrue(
            new ShootFromDistanceCommand(
                m_shooterSubsystem, m_limelightSubsystem, m_shooterLookupTable));
        m_operatorJoystick.leftBumper().whileTrue(new TowerUpCommand(m_towerSubsystem));
        m_operatorJoystick.rightBumper().whileTrue(new TowerDownCommand(m_towerSubsystem));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return m_autonomousFactory.getAutonomousCommand();
    }
}
