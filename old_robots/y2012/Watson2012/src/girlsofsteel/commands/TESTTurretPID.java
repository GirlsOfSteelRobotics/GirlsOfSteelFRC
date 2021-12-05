package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TESTTurretPID extends CommandBase {

    private double p;
    private double d;

    private double setpoint;

    public TESTTurretPID(){
        requires(turret);
        SmartDashboard.putNumber("Turret Setpoint", 0.0);
        SmartDashboard.putNumber("Turret P", 0.0);
        SmartDashboard.putNumber("Turret D", 0.0);
    }

    @Override
    protected void initialize() {
        turret.initEncoder();
        turret.enablePID();
    }

    @Override
    protected void execute() {
        p = SmartDashboard.getNumber("Turret P", 0.0);
        d = SmartDashboard.getNumber("Turret D", 0.0);
        turret.setPDs(p, d);
        setpoint = SmartDashboard.getNumber("Turret Setpoint", 0.0);
        turret.setPIDSetPoint(setpoint);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        turret.disablePID();
        turret.stopJag();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
