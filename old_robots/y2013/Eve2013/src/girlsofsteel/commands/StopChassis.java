package girlsofsteel.commands;

public class StopChassis extends CommandBase {

    public StopChassis(){
        requires(drive);
        requires(chassis);
    }

    protected void initialize() {
    }

    protected void execute() {
        chassis.stopJags();
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
