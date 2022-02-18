// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.rapidreact;

import com.gos.rapidreact.commands.HangerDownCommand;
import com.gos.rapidreact.commands.HangerUpCommand;
import com.gos.rapidreact.commands.HorizontalConveyorBackwardCommand;
import com.gos.rapidreact.commands.LimelightGoToCargoCommand;
import com.gos.rapidreact.commands.ShooterRpmPIDCommand;
import com.gos.rapidreact.commands.VerticalConveyorDownCommand;
import com.gos.rapidreact.commands.CollectorDownCommand;
import com.gos.rapidreact.commands.CollectorPivotPIDCommand;
import com.gos.rapidreact.commands.CollectorUpCommand;
import com.gos.rapidreact.commands.GoToCargoCommand;
import com.gos.rapidreact.commands.DisengageRatchetCommand;
import com.gos.rapidreact.commands.EngageRatchetCommand;
import com.gos.rapidreact.commands.HorizontalConveyorForwardCommand;
import com.gos.rapidreact.commands.RollerInCommand;
import com.gos.rapidreact.commands.RollerOutCommand;
import com.gos.rapidreact.commands.SetInitialOdometryCommand;
import com.gos.rapidreact.commands.TeleopArcadeChassisCommand;
import com.gos.rapidreact.commands.VerticalConveyorUpCommand;
import com.gos.rapidreact.commands.tuning.TuneCollectorPivotPIDGravityOffsetCommand;
import com.gos.rapidreact.commands.tuning.TuneShooterGoalRPMCommand;
import com.gos.rapidreact.commands.tuning.TuneShooterMotorSpeedCommand;
import com.gos.rapidreact.subsystems.ChassisSubsystem;
import com.gos.rapidreact.subsystems.CollectorSubsystem;
import com.gos.rapidreact.subsystems.IntakeLimelightSubsystem;
import edu.wpi.first.math.util.Units;
import com.gos.rapidreact.subsystems.HangerSubsystem;
import com.gos.rapidreact.subsystems.ShooterSubsystem;
import com.gos.rapidreact.subsystems.HorizontalConveyorSubsystem;
import com.gos.rapidreact.subsystems.VerticalConveyorSubsystem;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.simulation.DriverStationSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import com.gos.rapidreact.auto_modes.AutoModeFactory;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

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
    private final HangerSubsystem m_hanger = new HangerSubsystem();
    private final HorizontalConveyorSubsystem m_horizontalConveyor = new HorizontalConveyorSubsystem();
    private final VerticalConveyorSubsystem m_verticalConveyor = new VerticalConveyorSubsystem();
    private final ShooterSubsystem m_shooter = new ShooterSubsystem();
    //private final LEDManagerSubsystem m_led = new LEDManagerSubsystem();
    private final IntakeLimelightSubsystem m_intakeLimelight = new IntakeLimelightSubsystem();

    private final XboxController m_driverJoystick = new XboxController(0);
    private final XboxController m_operatorJoystick = new XboxController(1);

    private final AutoModeFactory m_autoModeFactory;

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */

    public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();
        m_autoModeFactory = new AutoModeFactory(m_chassis, m_shooter, m_verticalConveyor);

        SmartDashboard.putData("CollectorDownCommand", new CollectorDownCommand(m_collector));
        SmartDashboard.putData("CollectorUpCommand", new CollectorUpCommand(m_collector));
        SmartDashboard.putData("RollerInCommand", new RollerInCommand(m_collector));
        SmartDashboard.putData("RollerOutCommand", new RollerOutCommand(m_collector));
        SmartDashboard.putData("CollectorPivotPIDCommand - 0 Degrees", new CollectorPivotPIDCommand(m_collector, 0));
        SmartDashboard.putData("CollectorPivotPIDCommand - 45 Degrees", new CollectorPivotPIDCommand(m_collector, Math.toRadians(45)));
        SmartDashboard.putData("CollectorPivotPIDCommand - 90 Degrees", new CollectorPivotPIDCommand(m_collector, Math.toRadians(90)));
        SmartDashboard.putData("TuneCollectorPivotPIDGravityOffset", new TuneCollectorPivotPIDGravityOffsetCommand(m_collector));
        SmartDashboard.putData("GoToCargoCommand - 10 forward", new GoToCargoCommand(m_chassis, Units.feetToMeters(10), 0));
        SmartDashboard.putData("GoToCargoCommand - 10 forward, 10 left", new GoToCargoCommand(m_chassis, Units.feetToMeters(10), Units.feetToMeters(-10)));
        SmartDashboard.putData("GoToCargoCommand - 10 forward, 10 right", new GoToCargoCommand(m_chassis, Units.feetToMeters(10), Units.feetToMeters(10)));
        SmartDashboard.putData("SetInitialOdometry - 0, 0, 0", new SetInitialOdometryCommand(m_chassis, 0, 0, 0));
        SmartDashboard.putData("SetInitialOdometry - 0, 0, 45", new SetInitialOdometryCommand(m_chassis, 0, 0, 45));
        SmartDashboard.putData("EngageRatchetCommand", new EngageRatchetCommand(m_hanger));
        SmartDashboard.putData("DisengageRatchetCommand", new DisengageRatchetCommand(m_hanger));
        SmartDashboard.putData("ShooterSpeed", new TuneShooterMotorSpeedCommand(m_shooter));
        SmartDashboard.putData("HorizontalConveyorForwardCommand", new HorizontalConveyorForwardCommand(m_horizontalConveyor));
        SmartDashboard.putData("HorizontalConveyorBackwardCommand", new HorizontalConveyorBackwardCommand(m_horizontalConveyor));
        SmartDashboard.putData("VerticalConveyorUpCommand", new VerticalConveyorUpCommand(m_verticalConveyor));
        SmartDashboard.putData("VerticalConveyorDownCommand", new VerticalConveyorDownCommand(m_verticalConveyor));
        SmartDashboard.putData("ShooterPIDCommand - 3000", new ShooterRpmPIDCommand(m_shooter, 3000));
        SmartDashboard.putData("ShooterPIDCommand - 5000", new ShooterRpmPIDCommand(m_shooter, 5000));
        SmartDashboard.putData("TuneShooterGoalRPMCommand", new TuneShooterGoalRPMCommand(m_shooter));

        if (RobotBase.isSimulation()) {
            DriverStationSim.setEnabled(true);
        }
    }


    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        //driver
        m_chassis.setDefaultCommand(new TeleopArcadeChassisCommand(m_chassis, m_driverJoystick));

        final JoystickButton rollerIn = new JoystickButton(m_driverJoystick, XboxController.Button.kRightBumper.value); //right bumper
        rollerIn.whileHeld(new RollerInCommand(m_collector), true);
        final JoystickButton rollerOut = new JoystickButton(m_driverJoystick, XboxController.Button.kLeftBumper.value); //left bumper
        rollerOut.whileHeld(new RollerOutCommand(m_collector), true);
        final JoystickButton limelightGoToCargo = new JoystickButton(m_driverJoystick, XboxController.Button.kA.value);
        limelightGoToCargo.whenPressed(new LimelightGoToCargoCommand(m_chassis, m_intakeLimelight));
        new Button(() -> m_driverJoystick.getLeftTriggerAxis() > 0.5).whileHeld(new HangerUpCommand(m_hanger)); //left trigger
        new Button(() -> m_driverJoystick.getRightTriggerAxis() > 0.5).whileHeld(new HangerDownCommand(m_hanger)); //right trigger

        //operator
        final JoystickButton collectorDown = new JoystickButton(m_operatorJoystick, XboxController.Button.kLeftBumper.value); //left bumper
        collectorDown.whileHeld(new CollectorDownCommand(m_collector), true);
        final JoystickButton collectorUp = new JoystickButton(m_operatorJoystick, XboxController.Button.kRightBumper.value); //right bumper
        collectorUp.whileHeld(new CollectorUpCommand(m_collector), true);
        new Button(() -> m_operatorJoystick.getLeftY() > 0.8).whileHeld(new VerticalConveyorDownCommand(m_verticalConveyor)); //joystick left
        new Button(() -> m_operatorJoystick.getLeftY() < -0.8).whileHeld(new VerticalConveyorUpCommand(m_verticalConveyor)); //joystick left
        new Button(() -> m_operatorJoystick.getRightY() < -0.5).whileHeld(new HorizontalConveyorForwardCommand(m_horizontalConveyor)); //joystick right
        new Button(() -> m_operatorJoystick.getRightY() > 0.5).whileHeld(new HorizontalConveyorBackwardCommand(m_horizontalConveyor)); //joystick right
        final JoystickButton shooterRpmPID = new JoystickButton(m_operatorJoystick, XboxController.Axis.kRightTrigger.value); //joystick right
        shooterRpmPID.whileHeld(new ShooterRpmPIDCommand(m_shooter, 3000));

    }


    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return m_autoModeFactory.getAutonomousMode();
    }
}
