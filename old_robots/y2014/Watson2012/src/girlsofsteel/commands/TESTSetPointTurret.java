package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TESTSetPointTurret extends CommandBase {

    double angle;

    public TESTSetPointTurret() {
        requires(turret);
        SmartDashboard.putDouble("Turret Relative Angle", 0.0);
    }

    protected void initialize() {
        turret.initEncoder();
        turret.enablePID();
    }

    protected void execute() {
        angle = SmartDashboard.getDouble("Turret Relative Angle", 0.0);
        turret.setPIDSetPoint(turret.getEncoderDistance() + angle);
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
