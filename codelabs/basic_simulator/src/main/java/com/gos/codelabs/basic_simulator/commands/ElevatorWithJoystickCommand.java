package com.gos.codelabs.basic_simulator.commands;

import com.gos.codelabs.basic_simulator.subsystems.ElevatorSubsystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ElevatorWithJoystickCommand extends CommandBase {
    private final XboxController m_operatorJoystick;
    private final ElevatorSubsystem m_lift;

    public ElevatorWithJoystickCommand(ElevatorSubsystem lift, XboxController operatorJoystick) {
        m_lift = lift;
        m_operatorJoystick = operatorJoystick;

        addRequirements(lift);
    }

    @Override
    public void execute() {
        // TODO implement
    }
}
