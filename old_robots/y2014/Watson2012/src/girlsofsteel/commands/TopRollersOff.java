package girlsofsteel.commands;

public class TopRollersOff extends CommandBase {

    protected void initialize() {
    }

    protected void execute() {
        shooter.topRollersOff();
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
