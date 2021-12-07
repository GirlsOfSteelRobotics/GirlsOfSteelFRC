package girlsofsteel.commands;

public class ReverseCollectors extends CommandBase {

    public ReverseCollectors(){
        requires(collector);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        collector.reverseMiddleConveyor();
        collector.reverseBrush();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        collector.stopBrush();
        collector.stopMiddleConveyor();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
