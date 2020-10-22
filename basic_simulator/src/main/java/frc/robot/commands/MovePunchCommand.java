package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PunchSubsystem;

public class MovePunchCommand extends CommandBase {

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
