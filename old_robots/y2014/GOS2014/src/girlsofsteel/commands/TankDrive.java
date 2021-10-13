package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author Jisue
 */
public class TankDrive extends CommandBase {
/*  need values for joysticks
    need joystick here
    inputs are joysticks
    outputs are motors
    
    */
Joystick left;
Joystick right;
    
public TankDrive() {
  requires(driving);  
}    
    protected void initialize() {
        left = oi.getChassisJoystick();
        right = oi.getOperatorJoystick();
    }

    protected void execute() {
        //System.out.println("Right: " + right.getY() + "\nLeft: " + left.getY());
        chassis.tankDrive(right.getY(),left.getY());
        
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
