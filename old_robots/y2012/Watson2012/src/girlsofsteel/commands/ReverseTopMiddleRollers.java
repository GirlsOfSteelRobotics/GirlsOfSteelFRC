package girlsofsteel.commands;

public class ReverseTopMiddleRollers extends CommandBase {

    public ReverseTopMiddleRollers(){
        requires(collector);
        requires(shooter);
    }

    protected void initialize() {
    }

    protected void execute() {
        collector.reverseMiddleConveyor();
        shooter.topRollersBackward();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        collector.stopMiddleConveyor();
        shooter.topRollersOff();
    }

    protected void interrupted() {
        end();
    }

}
