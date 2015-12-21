package girlsofsteel.commands;

public class DelayReverseRollers extends CommandBase {

    protected void initialize() {
    }

    protected void execute() {
        if(timeSinceInitialized() > 4.0){
            collector.reverseBrush();
            collector.reverseMiddleConveyor();
            shooter.topRollersBackward();
        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        collector.stopBrush();
        collector.stopMiddleConveyor();
        shooter.topRollersOff();
    }

    protected void interrupted() {
        end();
    }
    
}
