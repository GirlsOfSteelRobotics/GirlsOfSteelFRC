package girlsofsteel.commands;

public class ReverseTopMiddleRollers extends CommandBase {

    public ReverseTopMiddleRollers(){
        requires(collector);
        requires(shooter);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        collector.reverseMiddleConveyor();
        shooter.topRollersBackward();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        collector.stopMiddleConveyor();
        shooter.topRollersOff();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
