/*
 * This will get a jag number and speed from Smart Dashboard and then
 * set the chosen jag to the given speed. If you just want to run all three
 * jags at the given speed, call the setJags(double speed) function instead
 * of the setJag function.
 */
package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Sylvie
 */
public class TestJags extends CommandBase{

    double speed;
    int numJag;
    
    public TestJags() {
        requires(chassis);
    }
    
    protected void initialize() {
        SmartDashboard.putNumber("Jag Speed", 0.0);
        SmartDashboard.putNumber("Jag Number", 0);
    }

    protected void execute() {
        speed = SmartDashboard.getNumber("Jag Speed");
        numJag = (int)SmartDashboard.getNumber("Jag Number");
        chassis.setJag(numJag, speed);
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
