package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;

public class Nudge extends CommandBase {

    Joystick driverJoystick;

    double xValue;

    public Nudge(){
        requires(chassis);
    }

    @Override
    protected void initialize() {
        chassis.initEncoders();
        chassis.initHoldPosition();
        driverJoystick = oi.getDriverJoystick();
    }

    @Override
    protected void execute() {
        xValue = driverJoystick.getX();
        if(-0.2 > xValue || xValue > 0.2){
            chassis.nudge(xValue);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        chassis.disablePositionPIDs();
        chassis.endEncoders();
        chassis.stopJags();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
