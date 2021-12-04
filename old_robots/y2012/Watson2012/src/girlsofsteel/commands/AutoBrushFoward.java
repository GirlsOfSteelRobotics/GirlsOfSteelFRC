package girlsofsteel.commands;

public class AutoBrushFoward extends CommandBase {

    public AutoBrushFoward() {
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
        System.out.println("Brush Forward Done");
        return timeSinceInitialized() > 5;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }
}
