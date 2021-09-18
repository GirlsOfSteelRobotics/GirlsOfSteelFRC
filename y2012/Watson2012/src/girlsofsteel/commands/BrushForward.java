package girlsofsteel.commands;

public class BrushForward extends CommandBase {

    public BrushForward() {
        requires(collector);
    }

    protected void initialize() {
    }

    protected void execute() {
        collector.forwardBrush();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
        end();
    }
}
