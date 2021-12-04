package girlsofsteel.commands;

public class TopRollersOff extends CommandBase {

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        shooter.topRollersOff();
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
