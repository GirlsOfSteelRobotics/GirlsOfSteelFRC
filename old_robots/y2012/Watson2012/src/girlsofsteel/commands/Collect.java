package girlsofsteel.commands;


public class Collect extends CommandBase {

    public Collect(){
        requires(collector);
    }

    @Override
    protected void initialize() {
        collector.forwardBrush();
        collector.forwardMiddleConveyor();
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
        collector.stopBrush();
        collector.stopMiddleConveyor();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
