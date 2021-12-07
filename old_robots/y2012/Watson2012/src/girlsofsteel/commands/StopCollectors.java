package girlsofsteel.commands;

public class StopCollectors extends CommandBase {

    public StopCollectors(){
        requires(collector);
    }

    @Override
    protected void initialize() {
        collector.stopBrush();
        collector.stopMiddleConveyor();
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        end();
    }

}
