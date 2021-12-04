package girlsofsteel.commands;

public class DisableShooter extends CommandBase {

    public DisableShooter(){
        requires(shooter);
    }

    @Override
    protected void initialize() {
        shooter.disablePID();
        shooter.stopJags();
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
