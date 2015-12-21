package girlsofsteel.commands;

public class MiddleCollectorsReverse extends CommandBase{

    public MiddleCollectorsReverse(){
        //requires(collector); Trevor said it was okay to not require this.
        //both the middle collector and brush both requrie the middle collector 
    }
    protected void initialize() {
    }

    protected void execute() {
        collector.reverseMiddleConveyor();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    end();
    }
    
}
