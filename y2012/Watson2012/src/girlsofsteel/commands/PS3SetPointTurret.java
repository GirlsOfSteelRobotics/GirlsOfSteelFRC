package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;

public class PS3SetPointTurret extends CommandBase {

    Joystick operatorJoystick;
    double angle;

    public PS3SetPointTurret() {
        requires(turret);
    }

    protected void initialize() {
        turret.initEncoder();
        turret.enablePID();
        operatorJoystick = oi.getOperatorJoystick();
    }

    protected void execute() {
        angle = operatorJoystick.getX()*5.0;
        if(angle < -0.5 || angle > 0.5){
            turret.setPIDSetPoint(turret.getEncoderDistance() + angle);
        }
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
