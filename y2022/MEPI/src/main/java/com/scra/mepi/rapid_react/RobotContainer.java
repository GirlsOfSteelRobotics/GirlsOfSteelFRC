// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.scra.mepi.rapid_react;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import com.scra.mepi.rapid_react.commands.ClimberCommand;
import com.scra.mepi.rapid_react.commands.ExampleCommand;
import com.scra.mepi.rapid_react.subsystems.ClimberSubsystem;
import com.scra.mepi.rapid_react.subsystems.ExampleSubsystem;
import com.scra.mepi.rapid_react.subsystems.IntakeSubsystem;
import com.scra.mepi.rapid_react.commands.ToggleIntakeCommand;
import com.scra.mepi.rapid_react.commands.RunIntakeCommand;
import com.scra.mepi.rapid_react.commands.TowerDownCommand;
import com.scra.mepi.rapid_react.commands.TowerUpCommand;
import com.scra.mepi.rapid_react.commands.TowerKickerCommand;
import com.scra.mepi.rapid_react.ShooterLookupTable;
import com.scra.mepi.rapid_react.commands.ShooterPIDCommand;
import com.scra.mepi.rapid_react.subsystems.TowerSubsystem;
import com.scra.mepi.rapid_react.subsystems.ShooterSubsytem;
import com.scra.mepi.rapid_react.subsystems.HopperSubsystem;
import com.scra.mepi.rapid_react.subsystems.IntakeSubsystem;
import com.scra.mepi.rapid_react.subsystems.LimelightSubsystem;
import com.scra.mepi.rapid_react.subsystems.DrivetrainSubsystem;
import com.scra.mepi.rapid_react.commands.DriveCommand;
import com.scra.mepi.rapid_react.commands.KickIfShootSetRPMCommand;
import com.scra.mepi.rapid_react.commands.KickIfShooterDistanceGoBrrCommand;
import com.scra.mepi.rapid_react.commands.HopperCommand;
import com.scra.mepi.rapid_react.commands.ToggleIntakeCommand;
import com.scra.mepi.rapid_react.commands.AutoAimCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import com.scra.mepi.rapid_react.commands.SetShooterSpeedCommand;

import com.scra.mepi.rapid_react.subsystems.ShooterSubsytem;
import com.scra.mepi.rapid_react.subsystems.TowerSubsystem;
import com.scra.mepi.rapid_react.ShooterLookupTable;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  private final XboxController m_driverJoystick = new XboxController(0);
  private final XboxController m_controlJoystick = new XboxController(1);
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
    private final IntakeSubsystem m_intakeSubsystem = new IntakeSubsystem();
    private final DrivetrainSubsystem m_drivetrainSubsystem = new DrivetrainSubsystem();
  // private final ClimberSubsystem m_climberSubsystem = new ClimberSubsystem();
    private final ShooterSubsytem m_shooterSubsystem = new ShooterSubsytem();
    private final TowerSubsystem m_towerSubsystem = new TowerSubsystem();
    private final HopperSubsystem m_hopperSubsystem = new HopperSubsystem();
    private final LimelightSubsystem m_limelightSubsystem = new LimelightSubsystem();
    private final ShooterLookupTable m_shooterLookupTable = new ShooterLookupTable();

  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);
    private final RunIntakeCommand m_intakeInCommand =
        new RunIntakeCommand(m_intakeSubsystem, 0.75, m_hopperSubsystem);
    private final RunIntakeCommand m_intakeOutCommand =
        new RunIntakeCommand(m_intakeSubsystem, -0.75, m_hopperSubsystem);
    private final RunIntakeCommand m_intakeStopCommand =
        new RunIntakeCommand(m_intakeSubsystem, 0, m_hopperSubsystem);
    private final DriveCommand m_driveCommand =
        new DriveCommand(m_drivetrainSubsystem, m_driverJoystick);
    private final ToggleIntakeCommand m_toggleIntakeCommand =
        new ToggleIntakeCommand(m_intakeSubsystem);
    private final TowerUpCommand m_towerUpCommand = new TowerUpCommand(m_towerSubsystem);
    private final TowerKickerCommand m_towerKickerCommand = new
  TowerKickerCommand(m_towerSubsystem);
    private final ShooterPIDCommand m_shooterPIDCommand =
        new ShooterPIDCommand(m_shooterSubsystem);
    private final KickIfShootSetRPMCommand m_kickIfShootSetRPMCommand =
        new KickIfShootSetRPMCommand(m_shooterSubsystem, m_towerSubsystem);

  public RobotContainer() {
    CameraServer.startAutomaticCapture();
    // Configure the button bindings
    configureButtonBindings();
    m_intakeSubsystem.setDefaultCommand(m_intakeStopCommand);
    m_hopperSubsystem.setDefaultCommand(new HopperCommand(m_hopperSubsystem, 0));
    // m_shooterSubsystem.setDefaultCommand(new ShooterPIDCommand(m_shooterSubsystem, 0));
    m_drivetrainSubsystem.setDefaultCommand(m_driveCommand);
    //m_climberSubsystem.setDefaultCommand(new ClimberCommand(m_climberSubsystem, 0));
    ShuffleboardTab testCommands = Shuffleboard.getTab("test commands");

    // testCommands.add("ClimbUp", new ClimberCommand(m_climberSubsystem, 0.25));
    // testCommands.add("ClimbDown", new ClimberCommand(m_climberSubsystem, -0.25));
    // testCommands.add("IntakeIn", new RunIntakeCommand(m_intakeSubsystem, 0.75,
    // m_hopperSubsystem));
    // testCommands.add(
    //     "IntakeOut", new RunIntakeCommand(m_intakeSubsystem, -0.75, m_hopperSubsystem));
    // testCommands.add(
    //     "Shoot RPM", new ShooterPIDCommand(m_shooterSubsystem, m_tunableShooterGoal.get()));
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

    // driver joystick
    new JoystickButton(m_driverJoystick, XboxController.Button.kLeftBumper.value)
        .whenHeld(m_intakeInCommand);
    new JoystickButton(m_driverJoystick, XboxController.Button.kRightBumper.value)
        .whenHeld(m_intakeOutCommand);
    new Button(() -> m_driverJoystick.getLeftTriggerAxis() > 0.5)
        .whileHeld(new ToggleIntakeCommand(m_intakeSubsystem));
    new Button(() -> m_driverJoystick.getRightTriggerAxis() > 0.5)
        .whileHeld(new AutoAimCommand(m_drivetrainSubsystem, m_limelightSubsystem));

    // control joystic
    new Button(() -> m_controlJoystick.getLeftY() > 0.75)
        .whileHeld(new TowerUpCommand(m_towerSubsystem));
    new Button(() -> m_controlJoystick.getLeftY() < -0.75)
        .whileHeld(new TowerDownCommand(m_towerSubsystem));
    // new Button(() -> m_controlJoystick.getRightY() > 0.75)
    //     .whileHeld(new ClimberCommand(m_climberSubsystem, 0.25));
    // new Button(() -> m_controlJoystick.getRightY() < -0.75)
    //     .whileHeld(new ClimberCommand(m_climberSubsystem, -0.25));
    new Button(() -> m_controlJoystick.getLeftTriggerAxis() > 0.5)
    .whileHeld(new KickIfShootSetRPMCommand(m_shooterSubsystem, m_towerSubsystem)).whenReleased(new SetShooterSpeedCommand(m_shooterSubsystem, 0));
    new Button(() -> m_controlJoystick.getRightTriggerAxis() > 0.5)
    .whileHeld(new KickIfShooterDistanceGoBrrCommand(m_shooterSubsystem, m_towerSubsystem, m_limelightSubsystem, m_shooterLookupTable)).whenReleased(new SetShooterSpeedCommand(m_shooterSubsystem, 0));
    new JoystickButton(m_controlJoystick, XboxController.Button.kLeftBumper.value)
        .whenHeld(m_shooterPIDCommand).whenReleased(new
    SetShooterSpeedCommand(m_shooterSubsystem, 0));

    new JoystickButton(m_controlJoystick, XboxController.Button.kX.value)
        .whenHeld(m_towerKickerCommand);

    // new JoystickButton(m_joystick, XboxController.Button.kB.value)
    // .whenPressed(m_intakeOutCommand);
    // new Button(() -> m_joystick.getLeftTriggerAxis() > 0.5).whileHeld(new
    // TowerKickerCommand(m_towerSubsystem));
    // new Button(() -> m_joystick.getRightTriggerAxis() > 0.5).whileHeld(new
    // ShooterPIDCommand(m_shooterSubsytem, m_tunableShooterGoal.get()));
    // new JoystickButton(m_joystick, XboxController.Button.kX.value)
    // .whenHeld(new SetShooterSpeedCommand(m_shooterSubsytem)).whenReleased(() ->
    // m_shooterSubsytem.setShooterSpeed(0));
    // new JoystickButton(m_controlJoystick, XboxController.Button.kLeftBumper.value)
    //     .whenPressed(new ClimberCommand(m_climberSubsystem, 0.5));
    // new JoystickButton(m_controlJoystick, XboxController.Button.kRightBumper.value)
    //     .whenPressed(new ClimberCommand(m_climberSubsystem, -0.5));
    // new POVButton(m_joystick, 0).whenPressed(new ToggleIntakeCommand(m_intakeSubsystem));
    // new POVButton(m_joystick, 180).whileHeld(new TowerDownCommand(m_towerSubsystem));

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
