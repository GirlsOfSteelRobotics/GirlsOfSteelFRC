package girlsofsteel.commands;

public class TopRollersReverse extends CommandBase {

    protected void initialize() {
    }

    protected void execute() {
        shooter.topRollersBackward();
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
