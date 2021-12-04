package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TESTTurretPID extends CommandBase {

    double p;
    double d;

    double setpoint;

    public TESTTurretPID(){
        requires(turret);
        SmartDashboard.putNumber("Turret Setpoint", 0.0);
        SmartDashboard.putNumber("Turret P", 0.0);
        SmartDashboard.putNumber("Turret D", 0.0);
    }

    protected void initialize() {
        turret.initEncoder();
        turret.enablePID();
    }

    protected void execute() {
        p = SmartDashboard.getNumber("Turret P", 0.0);
        d = SmartDashboard.getNumber("Turret D", 0.0);
        turret.setPDs(p, d);
        setpoint = SmartDashboard.getNumber("Turret Setpoint", 0.0);
        turret.setPIDSetPoint(setpoint);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        turret.disablePID();
        turret.stopJag();
    }

    protected void interrupted() {
        end();
    }

}
