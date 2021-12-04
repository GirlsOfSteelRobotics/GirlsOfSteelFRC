package girlsofsteel.commands;

public class MiddleCollectorsReverse extends CommandBase{

    public MiddleCollectorsReverse(){
        //requires(collector); Trevor said it was okay to not require this.
        //both the middle collector and brush both requrie the middle collector
    }
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
