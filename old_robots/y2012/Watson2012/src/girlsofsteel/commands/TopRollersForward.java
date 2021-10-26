package girlsofsteel.commands;

public class TopRollersForward extends CommandBase {

    protected void initialize() {
    }

    protected void execute() {
        shooter.topRollersForward();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        shooter.topRollersOff();
    }

    protected void interrupted() {
        end();
    }

}
