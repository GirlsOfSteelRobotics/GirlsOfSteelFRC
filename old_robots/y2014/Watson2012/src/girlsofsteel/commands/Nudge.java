package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;

public class Nudge extends CommandBase {

    Joystick driverJoystick;
    
    double xValue;
    
    public Nudge(){
        requires(chassis);
    }
    
    protected void initialize() {
        chassis.initEncoders();
        chassis.initHoldPosition();
        driverJoystick = oi.getDriverJoystick();
    }

    protected void execute() {
        xValue = driverJoystick.getX();
        if(-0.2 > xValue || xValue > 0.2){
            chassis.nudge(xValue);
        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        chassis.disablePositionPIDs();
        chassis.endEncoders();
        chassis.stopJags();
    }

    protected void interrupted() {
        end();
    }
    
}
