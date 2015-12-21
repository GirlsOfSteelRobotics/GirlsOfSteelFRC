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
 * Turns on a position pid which can be controlled through the SmartDashboard
 * @deprecated untested
 */
public class PositionControl extends CommandBase implements PIDSource, PIDOutput {

    private SendablePIDController pid;

    public PositionControl() {
        requires(chassis);

        pid = new SendablePIDController(1, 0, 0, this, this);

        SmartDashboard.putData("Position PID", pid);
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
        return chassis.getRotationsOfRightWheels();
    }

    public void pidWrite(double output) {
        chassis.setRight(output);
    }

}
