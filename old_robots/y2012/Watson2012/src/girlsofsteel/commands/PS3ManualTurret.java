package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;

public class PS3ManualTurret extends CommandBase {

    private Joystick operatorJoystick;

    private double speed;

    public PS3ManualTurret(){
        requires(turret);
    }

    @Override
    protected void initialize() {
        operatorJoystick = oi.getOperatorJoystick();
    }

    @Override
    protected void execute() {
        speed = operatorJoystick.getX()*.5;
        turret.setJagSpeed(speed);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        end();
    }

}
