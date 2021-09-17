/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.testbot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Prints out the encoder distance to the SmartDashboard.  It will go
 * until it is canceled.
 */
public class GetEncoderDistance extends CommandBase {

    protected void initialize() {
    }

    protected void execute() {
        double encoderDistance = chassis.getRotationsOfRightWheels();
        SmartDashboard.putDouble("Encoder Distance", encoderDistance);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
