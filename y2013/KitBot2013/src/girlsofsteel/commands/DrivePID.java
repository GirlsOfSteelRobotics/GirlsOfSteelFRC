package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Drives the robot with 1 joystick in arcade mode.
 */
public class DrivePID extends CommandBase {

    //create the objects needed (that will be assigned in initialize/execute
    Joystick joystick;
    double xAxis;
    double yAxis;
    
    public DrivePID() {
        //requires chassis subsystem so no other command can use it
        requires(chassisPID);
    }//end constructor

    // Called just before this Command runs the first time
    protected void initialize() {
        chassisPID.initEncoders();
        chassisPID.initPID();
        //assign the driver joystick from oi to be the joystick used here
        joystick = oi.getDriverJoystick();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        //get the xAxis & yAxis values
        xAxis = joystick.getX();
        yAxis = joystick.getY();
        //send to the drive method in chassis and execute the driving
        chassisPID.drivePID(xAxis, yAxis);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        chassisPID.stopPID();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        //when the command is interrupted, it will end
        end();
    }
}