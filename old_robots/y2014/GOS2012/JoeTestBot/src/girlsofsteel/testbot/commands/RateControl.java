/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package girlsofsteel.testbot.commands;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.smartdashboard.SendablePIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Puts on a rate pid which can be edited through the SmartDashboard.
 * @deprecated untested
 */
public class RateControl extends CommandBase implements PIDSource, PIDOutput {

    private SendablePIDController pid;

    public RateControl() {
        requires(chassis);

        pid = new SendablePIDController(1, 0, 0, this, this);

        SmartDashboard.putData("Rate PID", pid);
    }

    protected void initialize() {
        pid.enable();
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        pid.disable();
    }

    protected void interrupted() {
        end();
    }

    public double pidGet() {
        return chassis.getRateOfRightWheels();
    }

    public void pidWrite(double output) {
        chassis.setRight(output);
    }

}
