package girlsofsteel.commands;

public class SetPointTurret extends CommandBase {

    double knobValue;

    public SetPointTurret() {
        requires(turret);
    }

    @Override
    protected void initialize() {
        turret.initEncoder();
        turret.enablePID();
    }

    @Override
    protected void execute() {
        knobValue = oi.getTurretKnobValue(turret.TURRET_OVERRIDE_DEADZONE);
        turret.setPIDSetPoint(turret.getEncoderDistance() + knobValue);
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
        end();
    }
}
