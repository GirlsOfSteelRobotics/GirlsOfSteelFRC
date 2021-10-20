package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;

public class Shoot extends CommandBase {

    Joystick operatorJoystick;

    double speed;

    public Shoot(double speed){
        this.speed = speed;
        requires(shooter);
    }

    protected void initialize() {
        shooter.initEncoder();
        shooter.initPID();
        operatorJoystick = oi.getOperatorJoystick();
    }

    protected void execute() {
        shooter.shoot(speed);
        if(Math.abs(operatorJoystick.getThrottle()) >= 0.3 ||
                Math.abs(operatorJoystick.getTwist()) >= 0.3){
            shooter.topRollersForward();
        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        if(!oi.areTopRollersOverriden()){
            shooter.topRollersOff();
        }
        shooter.disablePID();
        shooter.stopEncoder();
    }

    protected void interrupted() {
        end();
    }

}
