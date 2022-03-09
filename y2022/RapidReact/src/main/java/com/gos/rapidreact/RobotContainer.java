// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.rapidreact;

import com.gos.rapidreact.commands.AutomatedVerticalConveyorCommand;
import com.gos.rapidreact.commands.HangerDownCommand;
import com.gos.rapidreact.commands.HangerUpCommand;
import com.gos.rapidreact.commands.DriveDistanceCommand;
import com.gos.rapidreact.commands.FeederVerticalConveyorBackwardCommand;
import com.gos.rapidreact.commands.FeederVerticalConveyorForwardCommand;
import com.gos.rapidreact.commands.debug.RightHangerDownCommand;
import com.gos.rapidreact.commands.debug.RightHangerUpCommand;
import com.gos.rapidreact.commands.ShootFromTableCommand;
import com.gos.rapidreact.commands.TurnToAngleCommand;
import com.gos.rapidreact.commands.GoToHubDistanceCommand;
import com.gos.rapidreact.commands.debug.LeftHangerDownCommand;
import com.gos.rapidreact.commands.HangerPIDCommand;
import com.gos.rapidreact.commands.debug.LeftHangerUpCommand;
import com.gos.rapidreact.commands.HorizontalConveyorBackwardCommand;
import com.gos.rapidreact.commands.LimelightGoToCargoCommand;
import com.gos.rapidreact.commands.ShooterFeederCommandGroup;
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
import com.gos.rapidreact.commands.tuning.ResetCollectorPivotEncoderCommand;
import com.gos.rapidreact.commands.tuning.TuneCollectorPivotPIDGravityOffsetCommand;
import com.gos.rapidreact.commands.tuning.TuneShooterGoalRPMCommand;
import com.gos.rapidreact.commands.tuning.TuneShooterMotorSpeedCommand;
import com.gos.rapidreact.commands.tuning.VelocityControlDrivingTuningCommand;
import com.gos.rapidreact.subsystems.ChassisSubsystem;
import com.gos.rapidreact.subsystems.CollectorSubsystem;
import com.gos.rapidreact.subsystems.IntakeLimelightSubsystem;
import com.gos.rapidreact.subsystems.LEDManagerSubsystem;
import com.gos.rapidreact.subsystems.ShooterLimelightSubsystem;
import com.gos.rapidreact.trajectory.TrajectoryB5;
import com.gos.rapidreact.trajectory.TrajectoryB54;
import com.gos.rapidreact.trajectory.TrajectoryCurve;
import com.gos.rapidreact.trajectory.TrajectorySCurve;
import edu.wpi.first.math.util.Units;
import com.gos.rapidreact.subsystems.HangerSubsystem;
import com.gos.rapidreact.subsystems.ShooterSubsystem;
import com.gos.rapidreact.subsystems.HorizontalConveyorSubsystem;
import com.gos.rapidreact.subsystems.VerticalConveyorSubsystem;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.simulation.DriverStationSim;
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
    private final LEDManagerSubsystem m_led = new LEDManagerSubsystem(); // NOPMD
    private final IntakeLimelightSubsystem m_intakeLimelight = new IntakeLimelightSubsystem();
    private final ShooterLimelightSubsystem m_shooterLimelight = new ShooterLimelightSubsystem();


    private final XboxController m_driverJoystick = new XboxController(0);
    private final XboxController m_operatorJoystick = new XboxController(1);

    private final AutoModeFactory m_autoModeFactory = new AutoModeFactory(m_chassis, m_shooter, m_verticalConveyor, m_horizontalConveyor, m_collector);

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */

    public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();

        ShuffleboardTab testCommands = Shuffleboard.getTab("test commands");
        ShuffleboardTab trajecCommands = Shuffleboard.getTab("trajectory commands");
        ShuffleboardTab widget = Shuffleboard.getTab("superstructure widgets");

        // Tuning
        testCommands.add("TuneShooterMotorSpeed", new TuneShooterMotorSpeedCommand(m_shooter));
        testCommands.add("TuneShooterGoalRPMCommand", new TuneShooterGoalRPMCommand(m_shooter));
        testCommands.add("TuneCollectorPivotPIDGravityOffset", new TuneCollectorPivotPIDGravityOffsetCommand(m_collector));
        testCommands.add("VelocityControlDrivingTuningCommand", new VelocityControlDrivingTuningCommand(m_chassis));

        // Sensor Reset
        testCommands.add("SetInitialOdometry - 0, 0, 0", new SetInitialOdometryCommand(m_chassis, 0, 0, 0));
        testCommands.add("SetInitialOdometry - 0, 0, 45", new SetInitialOdometryCommand(m_chassis, 0, 0, 45));
        testCommands.add("Reset Pivot Encoder", new ResetCollectorPivotEncoderCommand(m_collector));

        // testCommands.add("CollectorDownCommand", new CollectorDownCommand(m_collector));
        // testCommands.add("CollectorUpCommand", new CollectorUpCommand(m_collector));
        // testCommands.add("RollerInCommand", new RollerInCommand(m_collector));
        // testCommands.add("RollerOutCommand", new RollerOutCommand(m_collector));
        testCommands.add("CollectorPivotPIDCommand - 0 Degrees", new CollectorPivotPIDCommand(m_collector, 0));
        testCommands.add("CollectorPivotPIDCommand - 45 Degrees", new CollectorPivotPIDCommand(m_collector, 45));
        testCommands.add("CollectorPivotPIDCommand - 90 Degrees", new CollectorPivotPIDCommand(m_collector, 90));

        testCommands.add("EngageRatchetCommand", new EngageRatchetCommand(m_hanger));
        testCommands.add("DisengageRatchetCommand", new DisengageRatchetCommand(m_hanger));

        // testCommands.add("HorizontalConveyorForwardCommand", new HorizontalConveyorForwardCommand(m_horizontalConveyor));
        // testCommands.add("HorizontalConveyorBackwardCommand", new HorizontalConveyorBackwardCommand(m_horizontalConveyor));
        // testCommands.add("VerticalConveyorUpCommand", new VerticalConveyorUpCommand(m_verticalConveyor));
        // testCommands.add("VerticalConveyorDownCommand", new VerticalConveyorDownCommand(m_verticalConveyor));
        // testCommands.add("ShooterPIDCommand - 3000", new ShooterRpmPIDCommand(m_shooter, 3000));
        // testCommands.add("ShooterPIDCommand - 5000", new ShooterRpmPIDCommand(m_shooter, 5000));
        testCommands.add("HangerPIDCommand - 4", new HangerPIDCommand(m_hanger, Units.feetToMeters(4)));
        testCommands.add("HangerPIDCommand - 0", new HangerPIDCommand(m_hanger, Units.feetToMeters(0)));
        testCommands.add("ShooterTableRPMCommand", new ShootFromTableCommand(m_shooter, m_shooterLimelight));

        // Auton Sub-Commands
        testCommands.add("GoToCargoCommand - 10 forward", new GoToCargoCommand(m_chassis, Units.feetToMeters(10), 0));
        testCommands.add("GoToCargoCommand - 10 forward, 10 left", new GoToCargoCommand(m_chassis, Units.feetToMeters(10), Units.feetToMeters(-10)));
        testCommands.add("GoToCargoCommand - 10 forward, 10 right", new GoToCargoCommand(m_chassis, Units.feetToMeters(10), Units.feetToMeters(10)));

        testCommands.add("GoToHubAngle - 45", new TurnToAngleCommand(m_chassis, Math.toRadians(45)));
        testCommands.add("GoToHubAngle - 20", new TurnToAngleCommand(m_chassis, Math.toRadians(20)));

        testCommands.add("GoToHubDist - 10", new GoToHubDistanceCommand(m_chassis, m_shooterLimelight, Units.feetToMeters(10)));
        testCommands.add("GoToHubDist - 15", new GoToHubDistanceCommand(m_chassis, m_shooterLimelight, Units.feetToMeters(15)));

        testCommands.add("Drive Dist 60", new DriveDistanceCommand(m_chassis, Units.inchesToMeters(60), Units.inchesToMeters(2)));
        testCommands.add("Drive Dist -60", new DriveDistanceCommand(m_chassis, Units.inchesToMeters(-60), Units.inchesToMeters(2)));

        testCommands.add("Right Hang Up", new RightHangerUpCommand(m_hanger));
        testCommands.add("Right Hang Down", new RightHangerDownCommand(m_hanger));
        testCommands.add("Left Hang Up", new LeftHangerUpCommand(m_hanger));
        testCommands.add("Left Hang Down", new LeftHangerDownCommand(m_hanger));

        // Trajectories
        trajecCommands.add("B54", TrajectoryB54.fromBto5to4(m_chassis));
        trajecCommands.add("B5 (straight)", TrajectoryB5.fromBto5(m_chassis));
        trajecCommands.add("TestCurve", TrajectoryCurve.curve(m_chassis));
        trajecCommands.add("TestSCurve", TrajectorySCurve.scurve(m_chassis));

        widget.add("SuperstructureSendable", new SuperstructureSendable());

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
        m_chassis.setDefaultCommand(new TeleopArcadeChassisCommand(m_chassis, m_driverJoystick));

        final JoystickButton rollerIn = new JoystickButton(m_driverJoystick, XboxController.Button.kRightBumper.value); //right bumper
        rollerIn.whileHeld(new RollerInCommand(m_collector), true);
        final JoystickButton rollerOut = new JoystickButton(m_driverJoystick, XboxController.Button.kLeftBumper.value); //left bumper
        rollerOut.whileHeld(new RollerOutCommand(m_collector), true);
        final JoystickButton limelightGoToCargo = new JoystickButton(m_driverJoystick, XboxController.Button.kA.value);
        limelightGoToCargo.whenPressed(new LimelightGoToCargoCommand(m_chassis, m_intakeLimelight));

        //operator
        final JoystickButton collectorDown = new JoystickButton(m_operatorJoystick, XboxController.Button.kLeftBumper.value); //left bumper
        collectorDown.whileHeld(new CollectorDownCommand(m_collector), true);
        final JoystickButton collectorUp = new JoystickButton(m_operatorJoystick, XboxController.Button.kRightBumper.value); //right bumper
        collectorUp.whileHeld(new CollectorUpCommand(m_collector), true);
        new Button(() -> m_operatorJoystick.getLeftY() > 0.8).whileHeld(new VerticalConveyorDownCommand(m_verticalConveyor)); //joystick left
        new Button(() -> m_operatorJoystick.getLeftY() < -0.8).whileHeld(new VerticalConveyorUpCommand(m_verticalConveyor)); //joystick left
        new Button(() -> m_operatorJoystick.getRightY() < -0.5).whileHeld(new HorizontalConveyorForwardCommand(m_horizontalConveyor)); //joystick right
        new Button(() -> m_operatorJoystick.getRightY() > 0.5).whileHeld(new HorizontalConveyorBackwardCommand(m_horizontalConveyor)); //joystick right
        new Button(() -> m_operatorJoystick.getLeftTriggerAxis() > 0.5).whileHeld(new ShooterRpmPIDCommand(m_shooter, ShooterSubsystem.DEFAULT_SHOOTER_RPM)).whenReleased(() -> m_shooter.setShooterSpeed(0));
        new Button(() -> m_operatorJoystick.getRightTriggerAxis() > 0.5).whileHeld(new ShooterFeederCommandGroup(m_verticalConveyor, m_shooter, 10));
        new JoystickButton(m_operatorJoystick, XboxController.Button.kX.value).whileHeld(new HangerUpCommand(m_hanger));
        new JoystickButton(m_operatorJoystick, XboxController.Button.kB.value).whileHeld(new HangerDownCommand(m_hanger));
        new JoystickButton(m_operatorJoystick, XboxController.Button.kY.value).whileHeld(new FeederVerticalConveyorForwardCommand(m_verticalConveyor));
        //        new JoystickButton(m_operatorJoystick, XboxController.Button.kA.value).whileHeld(new FeederVerticalConveyorBackwardCommand(m_verticalConveyor));
        //        final JoystickButton automatedVerticalConveyor = new JoystickButton(m_operatorJoystick, XboxController.Button.kB.value);
        //        automatedVerticalConveyor.whileHeld(new AutomatedVerticalConveyorCommand(m_verticalConveyor, m_horizontalConveyor));

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

    private class SuperstructureSendable implements Sendable {



        @Override
        public void initSendable(SendableBuilder builder) {
            builder.setSmartDashboardType(SmartDashboardNames.SUPER_STRUCTURE);
            builder.addDoubleProperty(
                SmartDashboardNames.INTAKE_ANGLE, m_collector::getIntakeAngleDegrees, null);
            builder.addDoubleProperty(
                SmartDashboardNames.INTAKE_SPEED, m_collector::getPivotSpeed, null);
            builder.addDoubleProperty(
                SmartDashboardNames.HANGER_SPEED, m_hanger::getLeftHangerSpeed, null);
            builder.addDoubleProperty(
                SmartDashboardNames.HANGER_HEIGHT, m_hanger::getLeftHangerHeight, null);
            builder.addDoubleProperty(
                SmartDashboardNames.HORIZONTAL_CONVEYOR_SPEED, m_horizontalConveyor::getHorizontalConveyorSpeed, null);
            builder.addDoubleProperty(
                SmartDashboardNames.VERTICAL_CONVEYOR_SPEED, m_verticalConveyor::getVerticalConveyorSpeed, null);
            builder.addDoubleProperty(
                SmartDashboardNames.SHOOTER_SPEED, m_shooter::getShooterSpeed, null);
            builder.addDoubleProperty(
                SmartDashboardNames.ROLLER_SPEED, m_collector::getRollerSpeed, null);
            builder.addBooleanProperty(
                SmartDashboardNames.UPPER_VERTICAL_CONVEYOR_INDEXING_SENSOR, m_verticalConveyor::getUpperIndexSensor, null);
            builder.addBooleanProperty(
                SmartDashboardNames.LOWER_VERTICAL_CONVEYOR_INDEXING_SENSOR, m_verticalConveyor::getLowerIndexSensor, null);
            builder.addBooleanProperty(
                SmartDashboardNames.INTAKE_INDEXING_SENSOR, m_collector::getIndexSensor, null);
        }
    }
}
