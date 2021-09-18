package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TESTTurretPID extends CommandBase {

    double p;
    double d;
    
    double setpoint;
    
    public TESTTurretPID(){
        requires(turret);
        SmartDashboard.putDouble("Turret Setpoint", 0.0);
        SmartDashboard.putDouble("Turret P", 0.0);
        SmartDashboard.putDouble("Turret D", 0.0);
    }
    
    protected void initialize() {
        turret.initEncoder();
        turret.enablePID();
    }

    protected void execute() {
        p = SmartDashboard.getDouble("Turret P", 0.0);
        d = SmartDashboard.getDouble("Turret D", 0.0);
        turret.setPDs(p, d);
        setpoint = SmartDashboard.getDouble("Turret Setpoint", 0.0);
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
