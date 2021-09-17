package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;

public class DriveVelocityLinear extends CommandBase{

    Joystick joystick;
    double xAxis;
    double yAxis;
    
    //CHANGE ALL THESE (to real numbers)
    double deadzoneRange = 0.15;
    
    public DriveVelocityLinear(){
        requires(chassis);
    }
    
    protected void initialize() {
        joystick = oi.getJoystick();
        chassis.initRightEncoder();
        chassis.initLeftEncoder();
        chassis.initRightRatePID();
        chassis.initLeftRatePID();
    }

    protected void execute() {
        xAxis = joystick.getX();
        yAxis = joystick.getY();
        chassis.driveVelocityLinear(xAxis, yAxis, deadzoneRange);
    }

    protected boolean isFinished() {
        return false; //CHANGE THIS!!
    }

    protected void end() {
        chassis.endRightEncoder();
        chassis.endLeftEncoder();
        chassis.disableRightRatePID();
        chassis.disableLeftRatePID();
    }

    protected void interrupted() {
        end();
    }
    
}
