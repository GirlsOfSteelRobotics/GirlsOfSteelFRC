package girlsofsteel.commands;

public class PS3ThreeBallsNo extends CommandBase{
    
    public PS3ThreeBallsNo(){
        requires(collector);
    }
    
    protected void initialize() {
    }

    protected void execute() {
        collector.stopMiddleConveyor();
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
