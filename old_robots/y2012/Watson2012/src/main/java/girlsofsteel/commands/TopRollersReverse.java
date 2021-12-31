package girlsofsteel.commands;

import girlsofsteel.subsystems.Shooter;

public class TopRollersReverse extends CommandBase {
    private final Shooter m_shooter;

    public TopRollersReverse(Shooter shooter) {
        m_shooter = shooter;
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        m_shooter.topRollersBackward();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        m_shooter.topRollersOff();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
