package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;

public class DriveJagsSqrt extends CommandBase{

    Joystick joystick;
    double xAxis;
    double yAxis;
    
    //CHANGE TO REAL NUMBERS
    double deadzoneRange = 0.15;
    double scale = 1.0;
    
    public DriveJagsSqrt(){
        requires(chassis);
    }
    
    protected void initialize() {
        joystick = oi.getJoystick();
    }

    protected void execute() {
        xAxis = joystick.getX();
        yAxis = joystick.getY();
        chassis.driveJagsSqrt(xAxis, yAxis, deadzoneRange, scale);
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
