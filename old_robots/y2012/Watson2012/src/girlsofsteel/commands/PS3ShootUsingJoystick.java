package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;


public class PS3ShootUsingJoystick extends CommandBase{

    Joystick operatorJoystick;

    double speed;

    public PS3ShootUsingJoystick(){
        requires(shooter);
    }

    @Override
    protected void initialize() {
        operatorJoystick = oi.getOperatorJoystick();
        shooter.initEncoder();
        shooter.initPID();
    }

    @Override
    protected void execute() {
        speed = Math.abs(operatorJoystick.getZ())*40.0;
        shooter.shootUsingBallVelocity(speed);
        if(shooter.isWithinSetPoint(speed) && !oi.areTopRollersOverriden()){
            shooter.topRollersForward();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        if(!oi.areTopRollersOverriden()){
            shooter.topRollersOff();
        }
        shooter.disablePID();
        shooter.stopEncoder();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
