package girlsofsteel.commands;

public class DisableTurret extends CommandBase {

    public DisableTurret(){
        requires(turret);
    }

    @Override
    protected void initialize() {
        turret.disablePID();
        turret.stopJag();
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
        end();
    }

}
