package girlsofsteel.commands;

public class DelayReverseRollers extends CommandBase {

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        if(timeSinceInitialized() > 4.0){
            collector.reverseBrush();
            collector.reverseMiddleConveyor();
            shooter.topRollersBackward();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        collector.stopBrush();
        collector.stopMiddleConveyor();
        shooter.topRollersOff();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
