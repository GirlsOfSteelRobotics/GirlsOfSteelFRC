package com.gos.codelabs.basic_simulator.commands;

import com.gos.codelabs.basic_simulator.subsystems.ElevatorSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class ElevatorWithJoystickCommand extends Command {
    private final CommandXboxController m_operatorJoystick;
    private final ElevatorSubsystem m_lift;

    public ElevatorWithJoystickCommand(ElevatorSubsystem lift, CommandXboxController operatorJoystick) {
        m_lift = lift;
        m_operatorJoystick = operatorJoystick;

        addRequirements(lift);
    }

    @Override
    public void execute() {
        // TODO implement
    }
}
