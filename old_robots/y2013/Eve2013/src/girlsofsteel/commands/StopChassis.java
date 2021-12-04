package girlsofsteel.commands;

public class StopChassis extends CommandBase {

    public StopChassis(){
        requires(drive);
        requires(chassis);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        chassis.stopJags();
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
