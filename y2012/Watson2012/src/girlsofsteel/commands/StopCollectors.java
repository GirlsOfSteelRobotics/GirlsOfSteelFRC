package girlsofsteel.commands;

public class StopCollectors extends CommandBase {

    public StopCollectors(){
        requires(collector);
    }
    
    protected void initialize() {
        collector.stopBrush();
        collector.stopMiddleConveyor();
    }

    protected void execute() {
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
