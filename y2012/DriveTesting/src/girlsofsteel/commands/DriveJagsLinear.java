package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;

public class DriveJagsLinear extends CommandBase{

    Joystick joystick;
    double xAxis;
    double yAxis;
    
    //CHANGE TO REAL NUMBERS
    double deadzoneRange = 0.15;
    
    public DriveJagsLinear(){
        requires(chassis);
    }
    
    protected void initialize() {
        joystick = oi.getJoystick();
    }

    protected void execute() {
        xAxis = joystick.getX();
        yAxis = joystick.getY();
        chassis.driveJagsLinear(xAxis, yAxis, deadzoneRange);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        chassis.stopJags();
    }

    protected void interrupted() {
        end();
    }
    
}
