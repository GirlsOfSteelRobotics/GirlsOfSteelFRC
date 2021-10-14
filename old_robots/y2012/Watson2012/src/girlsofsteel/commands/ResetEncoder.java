package girlsofsteel.commands;

public class ResetEncoder extends CommandBase{

    protected void initialize() {
        chassis.initEncoders();
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
      
    }
    
}
