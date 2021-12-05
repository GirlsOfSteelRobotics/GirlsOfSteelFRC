package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;
import girlsofsteel.objects.Camera;

public class ShootBestFitLine extends CommandBase {

    private Joystick operatorJoystick;

    private double cameraDistance;

    public ShootBestFitLine(){
        requires(shooter);
    }

    @Override
    protected void initialize() {
        shooter.initEncoder();
        shooter.initPID();
        operatorJoystick = oi.getOperatorJoystick();
        cameraDistance = Camera.getXDistance();
    }

    @Override
    protected void execute() {
        shooter.autoShootBestFitLine(cameraDistance);
        if(Math.abs(operatorJoystick.getThrottle()) >= 0.3 ||
                Math.abs(operatorJoystick.getTwist()) >= 0.3){
            shooter.topRollersForward();
        }
    }

    @Override
    protected boolean isFinished() {
        return !Camera.isConnected();
    }

    @Override
    protected void end() {
        shooter.topRollersOff();
        shooter.disablePID();
        shooter.stopEncoder();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
