/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package girlsofsteel.commands;

import girlsofsteel.subsystems.Gripper;

/**
 * @author sam
 */
public class CloseBottomGrip extends CommandBase {

    private final Gripper m_gripper;

    public CloseBottomGrip(Gripper gripper) {
        m_gripper = gripper;
        requires(m_gripper);
    }

    //Command for closing the bottom gripper around the pyramid


    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {

        m_gripper.closeGrip();
    }

    @Override
    protected boolean isFinished() {
        return true;

    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }

}
