package com.gos.codelabs.basic_simulator.commands;

import com.gos.codelabs.basic_simulator.subsystems.PunchSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class MovePunchCommand extends Command {

    private final PunchSubsystem m_punch;
    private final boolean m_extendPunch;

    public MovePunchCommand(PunchSubsystem punch, boolean extendPunch) {
        m_punch = punch;
        m_extendPunch = extendPunch;

        addRequirements(m_punch);
    }

    @Override
    public void execute() {
        // TODO implement
    }
}
