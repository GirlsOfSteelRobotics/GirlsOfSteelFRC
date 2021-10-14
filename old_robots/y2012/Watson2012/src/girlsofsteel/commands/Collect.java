package girlsofsteel.commands;


public class Collect extends CommandBase {

    public Collect(){
        requires(collector);
    }
    
    protected void initialize() {
        collector.forwardBrush();
        collector.forwardMiddleConveyor();
    }
    
    protected void execute() {
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