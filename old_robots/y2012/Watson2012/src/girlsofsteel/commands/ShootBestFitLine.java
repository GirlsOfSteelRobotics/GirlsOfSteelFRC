package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;
import girlsofsteel.objects.Camera;

public class ShootBestFitLine extends CommandBase {

    Joystick operatorJoystick;

    double cameraDistance;

    public ShootBestFitLine(){
        requires(shooter);
    }

    protected void initialize() {
        shooter.initEncoder();
        shooter.initPID();
        operatorJoystick = oi.getOperatorJoystick();
        cameraDistance = Camera.getXDistance();
    }

    protected void execute() {
        shooter.autoShootBestFitLine(cameraDistance);
        if(Math.abs(operatorJoystick.getThrottle()) >= 0.3 ||
                Math.abs(operatorJoystick.getTwist()) >= 0.3){
            shooter.topRollersForward();
        }
    }

    protected boolean isFinished() {
        return !Camera.isConnected();
    }

    protected void end() {
        shooter.topRollersOff();
        shooter.disablePID();
        shooter.stopEncoder();
    }

    protected void interrupted() {
        end();
    }

}
