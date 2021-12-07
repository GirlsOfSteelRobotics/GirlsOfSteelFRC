package girlsofsteel.commands;

public class DisableChassis extends CommandBase {

    public DisableChassis(){
        requires(chassis);
    }

    @Override
    protected void initialize() {
        chassis.stopJags();
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
    }

}
