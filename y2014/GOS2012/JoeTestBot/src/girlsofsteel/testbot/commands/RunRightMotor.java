
package girlsofsteel.testbot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Runs the right motor at a speed set in the SmartDashboard
 */
public class RunRightMotor extends CommandBase {

    public RunRightMotor() {
        requires (chassis);

        SmartDashboard.putDouble("speed", 0);
    }

    protected void initialize() {
    }

    protected void execute() {
        chassis.setRight(SmartDashboard.getDouble("speed", 0));
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        chassis.setRight(0);
    }

    protected void interrupted() {
        end();
    }
}
