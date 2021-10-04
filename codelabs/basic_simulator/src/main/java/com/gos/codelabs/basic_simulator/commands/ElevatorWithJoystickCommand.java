package com.gos.codelabs.basic_simulator.commands;

import com.gos.codelabs.basic_simulator.subsystems.ElevatorSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.codelabs.basic_simulator.OI;

public class ElevatorWithJoystickCommand extends CommandBase {
    private final OI m_oi;
    private final ElevatorSubsystem m_lift;

    public ElevatorWithJoystickCommand(ElevatorSubsystem lift, OI oi) {
        m_lift = lift;
        m_oi = oi;

        addRequirements(lift);
    }

    @Override
    public void execute() {
        // TODO implement
    }
}
