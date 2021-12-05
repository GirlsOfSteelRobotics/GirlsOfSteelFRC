package girlsofsteel.commands;

public class TopRollersForward extends CommandBase {

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        shooter.topRollersForward();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        shooter.topRollersOff();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
