package girlsofsteel.commands;

public class DisableTurret extends CommandBase {

    public DisableTurret(){
        requires(turret);
    }

    protected void initialize() {
        turret.disablePID();
        turret.stopJag();
    }

    protected void execute() {
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
