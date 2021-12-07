package girlsofsteel.commands;

public class BrushForward extends CommandBase {

    public BrushForward() {
        requires(collector);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        collector.forwardBrush();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        end();
    }
}
