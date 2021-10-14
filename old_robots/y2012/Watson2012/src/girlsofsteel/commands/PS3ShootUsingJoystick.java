package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;


public class PS3ShootUsingJoystick extends CommandBase{

    Joystick operatorJoystick;

    double speed;

    public PS3ShootUsingJoystick(){
        requires(shooter);
    }

    protected void initialize() {
        operatorJoystick = oi.getOperatorJoystick();
        shooter.initEncoder();
        shooter.initPID();
    }

    protected void execute() {
        speed = Math.abs(operatorJoystick.getZ())*40.0;
        shooter.shootUsingBallVelocity(speed);
        if(shooter.isWithinSetPoint(speed) && !oi.areTopRollersOverriden()){
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
