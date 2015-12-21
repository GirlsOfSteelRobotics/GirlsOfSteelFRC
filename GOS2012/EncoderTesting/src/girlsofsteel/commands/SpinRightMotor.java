
package girlsofsteel.commands;


public class SpinRightMotor extends CommandBase{

    protected void initialize() {
    }

    protected void execute() 
    {chassis.spinRightMotor(oi.getJoystick());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
}
