package girlsofsteel.commands;

public class ManualTurret extends CommandBase {

    double knobValue;
    double speed;

    public ManualTurret() {
        requires(turret);
    }

    protected void initialize() {
        turret.disablePID();
    }

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

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        turret.stopJag();
    }

    protected void interrupted() {
    }
}
