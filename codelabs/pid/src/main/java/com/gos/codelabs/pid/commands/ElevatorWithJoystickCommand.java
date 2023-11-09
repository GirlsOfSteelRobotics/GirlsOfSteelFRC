package com.gos.codelabs.pid.commands;

import com.gos.codelabs.pid.subsystems.ElevatorSubsystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;

public class ElevatorWithJoystickCommand extends Command {
    private static final double ELEVATOR_JOYSTICK_DEADBAND = .01;

    private final XboxController m_operatorJoystick;
    private final ElevatorSubsystem m_lift;

    public ElevatorWithJoystickCommand(ElevatorSubsystem lift, XboxController operatorJoystick) {
        m_lift = lift;
        m_operatorJoystick = operatorJoystick;

        addRequirements(lift);
    }

    @Override
    public void execute() {
        m_lift.setSpeed(getElevatorJoystick());
    }

    private double getElevatorJoystick() {
        // Y is negated so that pushing the joystick forward results in positive values
        double output = -m_operatorJoystick.getRightY();

        // Ignore the noise that can happen when the joystick is neutral, but not perfectly 0.0
        if (Math.abs(output) < ELEVATOR_JOYSTICK_DEADBAND) {
            output = 0;
        }

        return output;
    }
}
