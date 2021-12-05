package girlsofsteel.commands;

public class PS3ThreeBallsNo extends CommandBase{

    public PS3ThreeBallsNo(){
        requires(collector);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        collector.stopMiddleConveyor();
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
