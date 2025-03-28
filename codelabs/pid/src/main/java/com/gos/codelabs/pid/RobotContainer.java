// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.codelabs.pid;

import com.gos.codelabs.pid.auton_modes.AutonFactory;
import com.gos.codelabs.pid.commands.DriveChassisHaloDrive;
import com.gos.codelabs.pid.commands.ElevatorToPositionCommand;
import com.gos.codelabs.pid.commands.ElevatorWithJoystickCommand;
import com.gos.codelabs.pid.commands.ShooterRpmCommand;
import com.gos.codelabs.pid.subsystems.ChassisSubsystem;
import com.gos.codelabs.pid.subsystems.ElevatorSubsystem;
import com.gos.codelabs.pid.subsystems.PunchSubsystem;
import com.gos.codelabs.pid.subsystems.ShooterSubsystem;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    private final XboxController m_driverJoystick;
    private final XboxController m_operatorJoystick;

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

        m_driverJoystick = new XboxController(0);
        m_operatorJoystick = new XboxController(1);

        SuperstructureSendable superstructureSendable = new SuperstructureSendable();
        Shuffleboard.getTab("Command Tester").add("Super Structure Widget", superstructureSendable)
                .withWidget(SmartDashboardNames.WIDGET_NAME)
                .withSize(4, 4);
        Shuffleboard.getTab("Super Structure").add("Super Structure Widget", superstructureSendable)
                .withWidget(SmartDashboardNames.WIDGET_NAME)
                .withSize(4, 4);


        new CommandTester(this);
        m_autonFactory = new AutonFactory();

        configureButtonBindings();
    }


    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        // Chassis
        m_chassisSubsystem.setDefaultCommand(new DriveChassisHaloDrive(m_chassisSubsystem, m_driverJoystick));

        // Elevator
        m_elevatorSubsystem.setDefaultCommand(new ElevatorWithJoystickCommand(m_elevatorSubsystem, m_operatorJoystick));
        new JoystickButton(m_operatorJoystick, XboxController.Button.kB.value).whileTrue(new ElevatorToPositionCommand(m_elevatorSubsystem, ElevatorSubsystem.Positions.LOW));
        new JoystickButton(m_operatorJoystick, XboxController.Button.kY.value).whileTrue(new ElevatorToPositionCommand(m_elevatorSubsystem, ElevatorSubsystem.Positions.MID));
        new JoystickButton(m_operatorJoystick, XboxController.Button.kX.value).whileTrue(new ElevatorToPositionCommand(m_elevatorSubsystem, ElevatorSubsystem.Positions.HIGH));

        // Shooter
        new JoystickButton(m_operatorJoystick, XboxController.Button.kLeftBumper.value).whileTrue(new ShooterRpmCommand(m_shooterSubsystem, 3200));
        new JoystickButton(m_operatorJoystick, XboxController.Button.kRightBumper.value).whileTrue(new ShooterRpmCommand(m_shooterSubsystem, 2500));

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

    private class SuperstructureSendable implements Sendable {

        @Override
        public void initSendable(SendableBuilder builder) {
            builder.setSmartDashboardType(SmartDashboardNames.SUPER_STRUCTURE_TABLE_NAME);

            builder.addDoubleProperty(SmartDashboardNames.ELEVATOR_TABLE_NAME + "/" + SmartDashboardNames.ELEVATOR_MOTOR_SPEED, m_elevatorSubsystem::getMotorSpeed, null);
            builder.addDoubleProperty(SmartDashboardNames.ELEVATOR_TABLE_NAME + "/" + SmartDashboardNames.ELEVATOR_HEIGHT, m_elevatorSubsystem::getHeightInches, null);
            builder.addDoubleProperty(SmartDashboardNames.ELEVATOR_TABLE_NAME + "/" + SmartDashboardNames.ELEVATOR_DESIRED_HEIGHT, m_elevatorSubsystem::getDesiredHeightInches, null);
            builder.addDoubleProperty(SmartDashboardNames.ELEVATOR_TABLE_NAME + "/" + SmartDashboardNames.ELEVATOR_VELOCITY, m_elevatorSubsystem::getVelocityMps, null);
            builder.addBooleanProperty(SmartDashboardNames.ELEVATOR_TABLE_NAME + "/" + SmartDashboardNames.ELEVATOR_UPPER_LIMIT_SWITCH, m_elevatorSubsystem::isAtUpperLimit, null);
            builder.addBooleanProperty(SmartDashboardNames.ELEVATOR_TABLE_NAME + "/" + SmartDashboardNames.ELEVATOR_LOWER_LIMIT_SWITCH, m_elevatorSubsystem::isAtLowerLimit, null);

            builder.addBooleanProperty(SmartDashboardNames.PUNCH_TABLE_NAME + "/" + SmartDashboardNames.PUNCH_IS_EXTENDED, m_punchSubsystem::isExtended, null);

            builder.addDoubleProperty(SmartDashboardNames.SPINNING_WHEEL_TABLE_NAME + "/" + SmartDashboardNames.SPINNING_WHEEL_MOTOR_SPEED, m_shooterSubsystem::getMotorSpeed, null);
            builder.addDoubleProperty(SmartDashboardNames.SPINNING_WHEEL_TABLE_NAME + "/" + SmartDashboardNames.SPINNING_WHEEL_RPM, m_shooterSubsystem::getRpm, null);
            builder.addDoubleProperty(SmartDashboardNames.SPINNING_WHEEL_TABLE_NAME + "/" + SmartDashboardNames.SPINNING_WHEEL_DESIRED_RPM, m_shooterSubsystem::getDesiredRpm, null);
        }
    }
}
