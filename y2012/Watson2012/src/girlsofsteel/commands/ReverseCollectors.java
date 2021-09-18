package girlsofsteel.commands;

public class ReverseCollectors extends CommandBase {

    public ReverseCollectors(){
        requires(collector);
    }
    
    protected void initialize() {
    }

    protected void execute() {
        collector.reverseMiddleConveyor();
        collector.reverseBrush();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        collector.stopBrush();
        collector.stopMiddleConveyor();
    }

    protected void interrupted() {
        end();
    }
            
}
