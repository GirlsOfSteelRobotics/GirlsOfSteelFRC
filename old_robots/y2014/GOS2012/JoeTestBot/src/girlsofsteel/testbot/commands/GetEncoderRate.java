package girlsofsteel.testbot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Prints out the encoder rate to the SmartDashboard.
 */
public class GetEncoderRate extends CommandBase {

    protected void initialize() {
    }

    protected void execute() {
        double encoderRate = chassis.getRateOfRightWheels();

        SmartDashboard.putDouble("Encoder Rate", encoderRate);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
