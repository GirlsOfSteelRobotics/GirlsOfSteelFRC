package girlsofsteel.commands;

public class ManualTurret extends CommandBase {

    double knobValue;
    double speed;

    public ManualTurret() {
        requires(turret);
    }

    @Override
    protected void initialize() {
        turret.disablePID();
    }

    @Override
    protected void execute() {
        knobValue = oi.getTurretKnobValue(turret.TURRET_OVERRIDE_DEADZONE);
        if (knobValue > 0) {
            speed = 0.2;
        }
        else{
            speed = -0.2;
        }
        turret.setJagSpeed(speed);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        turret.stopJag();
    }

    @Override
    protected void interrupted() {
    }
}
