package girlsofsteel.commands;

public class DisableShooter extends CommandBase {

    public DisableShooter(){
        requires(shooter);
    }
    
    protected void initialize() {
        shooter.disablePID();
        shooter.stopJags();
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
