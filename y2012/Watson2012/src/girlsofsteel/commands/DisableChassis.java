package girlsofsteel.commands;

public class DisableChassis extends CommandBase {

    public DisableChassis(){
        requires(chassis);
    }
    
    protected void initialize() {
        chassis.stopJags();
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
}
