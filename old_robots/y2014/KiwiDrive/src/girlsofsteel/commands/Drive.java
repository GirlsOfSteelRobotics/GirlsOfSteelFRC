
package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;

public class Drive extends CommandBase {

    Joystick joystick;
    double x;
    double y;
    double th;
    
    public Drive() {
//        requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        joystick = oi.getDrivingJoystick();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        //System.out.println("execute");
        x = joystick.getX();
        y = joystick.getY();
        th = joystick.getZ();
        System.out.println("Joystick X: " + x + "\nJoystick Y: " + y + "\nJoystick Theta" + th);
        chassis.driveVoltage(x, y, th);
        //Print what speed the wheel is actually going at
        //System.out.println("Encoder Speed: " + chassis.)
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        chassis.stopJags();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
