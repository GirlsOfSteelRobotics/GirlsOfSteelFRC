/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

import girlsofsteel.subsystems.Gripper;

/**
 *
 * @author sam
 */
public class OpenGripAtBar extends CommandBase {
    Gripper gripper;

    public OpenGripAtBar(Gripper gripper) {
        requires (gripper);
        this.gripper = gripper;
    }

    protected void initialize() {
        
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return gripper.atBar();
    }

    protected void end() {
        gripper.openGrip();
    }

    protected void interrupted() {
    }
}
