package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;

public class PS3SetPointTurret extends CommandBase {

    Joystick operatorJoystick;
    double angle;

    public PS3SetPointTurret() {
        requires(turret);
    }

    @Override
    protected void initialize() {
        turret.initEncoder();
        turret.enablePID();
        operatorJoystick = oi.getOperatorJoystick();
    }

    @Override
    protected void execute() {
        angle = operatorJoystick.getX()*5.0;
        if(angle < -0.5 || angle > 0.5){
            turret.setPIDSetPoint(turret.getEncoderDistance() + angle);
        }
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
