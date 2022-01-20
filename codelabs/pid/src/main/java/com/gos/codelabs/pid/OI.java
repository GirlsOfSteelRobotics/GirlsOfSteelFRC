package com.gos.codelabs.pid;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import com.gos.codelabs.pid.commands.DriveChassisHaloDrive;
import com.gos.codelabs.pid.commands.ElevatorToPositionCommand;
import com.gos.codelabs.pid.commands.ElevatorWithJoystickCommand;
import com.gos.codelabs.pid.commands.ShooterRpmCommand;
import com.gos.codelabs.pid.subsystems.ChassisSubsystem;
import com.gos.codelabs.pid.subsystems.ElevatorSubsystem;
import com.gos.codelabs.pid.subsystems.ShooterSubsystem;

public class OI {

    public static final double ELEVATOR_JOYSTICK_DEADBAND = .01;

    private final XboxController m_driverJoystick;
    private final XboxController m_operatorJoystick;

    @SuppressWarnings("PMD.UnusedFormalParameter")
    public OI(RobotContainer robotContainer) {
        m_driverJoystick = new XboxController(0);
        m_operatorJoystick = new XboxController(1);

        // Alias for readability
        ChassisSubsystem chassis = robotContainer.getChassis();
        ElevatorSubsystem lift = robotContainer.getElevator();
        ShooterSubsystem shooter = robotContainer.getShooter();

        // Chassis
        chassis.setDefaultCommand(new DriveChassisHaloDrive(chassis, this));

        // Elevator
        lift.setDefaultCommand(new ElevatorWithJoystickCommand(lift, this));
        new JoystickButton(m_operatorJoystick, XboxController.Button.kB.value).whileHeld(new ElevatorToPositionCommand(lift, ElevatorSubsystem.Positions.LOW));
        new JoystickButton(m_operatorJoystick, XboxController.Button.kY.value).whileHeld(new ElevatorToPositionCommand(lift, ElevatorSubsystem.Positions.MID));
        new JoystickButton(m_operatorJoystick, XboxController.Button.kX.value).whileHeld(new ElevatorToPositionCommand(lift, ElevatorSubsystem.Positions.HIGH));

        // Shooter
        new JoystickButton(m_operatorJoystick, XboxController.Button.kBumperLeft.value).whileHeld(new ShooterRpmCommand(shooter, 3200));
        new JoystickButton(m_operatorJoystick, XboxController.Button.kBumperRight.value).whileHeld(new ShooterRpmCommand(shooter, 2500));

    }

    public double getDriverThrottle() {
        // Y is negated so that pushing the joystick forward results in positive values
        return -m_driverJoystick.getLeftY();
    }

    public double getDriverSpin() {
        return m_driverJoystick.getRightX();
    }

    public double getElevatorJoystick() {
        // Y is negated so that pushing the joystick forward results in positive values
        double output = -m_operatorJoystick.getRightY();

        // Ignore the noise that can happen when the joystick is neutral, but not perfectly 0.0
        if (Math.abs(output) < ELEVATOR_JOYSTICK_DEADBAND) {
            output = 0;
        }

        return output;
    }

}
