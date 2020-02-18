package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Winch;

public class WinchWind extends CommandBase {

    private final Winch m_winchWind;
    private final boolean m_wind;

    public WinchWind(Winch winchWind, boolean wind) {
        this.m_winchWind = winchWind;
        this.m_wind = wind;

        super.addRequirements(winchWind);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if (m_wind) {
            m_winchWind.wind();
        }
        else {
            m_winchWind.unwind();
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_winchWind.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
