package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;

public class Shoot extends CommandBase {

    private Joystick operatorJoystick;

    private final double speed;

    public Shoot(double speed){
        this.speed = speed;
        requires(shooter);
    }

    @Override
    protected void initialize() {
        shooter.initEncoder();
        shooter.initPID();
        operatorJoystick = oi.getOperatorJoystick();
    }

    @Override
    protected void execute() {
        shooter.shoot(speed);
        if(Math.abs(operatorJoystick.getThrottle()) >= 0.3 ||
                Math.abs(operatorJoystick.getTwist()) >= 0.3){
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
