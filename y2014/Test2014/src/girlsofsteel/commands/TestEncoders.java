/*
 * This function will put a SmartDashboard print statement up that prints
 * the value of all two jag encoders
 */
package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Sylvie
 */
public class TestEncoders extends CommandBase{
    
    public TestEncoders() {
        requires(chassis);
    }

    protected void initialize() {
        chassis.startEncoders();
    }

    protected void execute() {
        SmartDashboard.putNumber("Encoder 1", chassis.getEncoders(1));
        SmartDashboard.putNumber("Encoder 2", chassis.getEncoders(2));
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        chassis.stopEncoders();
    }

    protected void interrupted() {
        end();
    }
    
}
