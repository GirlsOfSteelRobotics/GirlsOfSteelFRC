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
    private final Gripper gripper;

    public OpenGripAtBar(Gripper gripper) {
        requires (gripper);
        this.gripper = gripper;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return gripper.atBar();
    }

    @Override
    protected void end() {
        gripper.openGrip();
    }

    @Override
    protected void interrupted() {
    }
}
