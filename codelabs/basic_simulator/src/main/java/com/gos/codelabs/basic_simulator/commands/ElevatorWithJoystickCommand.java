package com.gos.codelabs.basic_simulator.commands;

import com.gos.codelabs.basic_simulator.subsystems.ElevatorSubsystem;
import org.wpilib.command2.Command;
import org.wpilib.command2.button.CommandNiDsXboxController;

public class ElevatorWithJoystickCommand extends Command {
    private final CommandNiDsXboxController m_operatorJoystick;
    private final ElevatorSubsystem m_lift;

    public ElevatorWithJoystickCommand(ElevatorSubsystem lift, CommandNiDsXboxController operatorJoystick) {
        m_lift = lift;
        m_operatorJoystick = operatorJoystick;

        addRequirements(lift);
    }

    @Override
    public void execute() {
        // TODO implement
    }
}
