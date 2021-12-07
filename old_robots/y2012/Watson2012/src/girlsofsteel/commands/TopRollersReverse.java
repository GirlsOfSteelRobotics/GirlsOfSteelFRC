package girlsofsteel.commands;

public class TopRollersReverse extends CommandBase {

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        shooter.topRollersBackward();
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
