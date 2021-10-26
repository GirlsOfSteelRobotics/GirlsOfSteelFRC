package girlsofsteel.commands;

public class SetPointTurret extends CommandBase {

    double knobValue;

    public SetPointTurret() {
        requires(turret);
    }

    protected void initialize() {
        turret.initEncoder();
        turret.enablePID();
    }

    protected void execute() {
        knobValue = oi.getTurretKnobValue(turret.TURRET_OVERRIDE_DEADZONE);
        turret.setPIDSetPoint(turret.getEncoderDistance() + knobValue);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        turret.stopJag();
    }

    protected void interrupted() {
        end();
    }
}
