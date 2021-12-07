package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TESTSetPointTurret extends CommandBase {

    private double angle;

    public TESTSetPointTurret() {
        requires(turret);
        SmartDashboard.putNumber("Turret Relative Angle", 0.0);
    }

    @Override
    protected void initialize() {
        turret.initEncoder();
        turret.enablePID();
    }

    @Override
    protected void execute() {
        angle = SmartDashboard.getNumber("Turret Relative Angle", 0.0);
        turret.setPIDSetPoint(turret.getEncoderDistance() + angle);
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
