package girlsofsteel.commands;

public class MiddleCollectorsReverse extends CommandBase{

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        collector.reverseMiddleConveyor();
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
