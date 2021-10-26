package girlsofsteel.commands;

public class AutoBrushFoward extends CommandBase {

    public AutoBrushFoward() {
        requires(collector);
    }

    protected void initialize() {
    }

    protected void execute() {
        collector.forwardBrush();
    }

    protected boolean isFinished() {
        System.out.println("Brush Forward Done");
        return timeSinceInitialized() > 5;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
