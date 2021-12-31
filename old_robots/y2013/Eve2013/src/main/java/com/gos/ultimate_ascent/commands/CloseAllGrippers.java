/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.ultimate_ascent.commands;

import com.gos.ultimate_ascent.subsystems.Gripper;

/**
 * @author sam
 */
public class CloseAllGrippers extends CommandBase {

    private final Gripper m_gripper;

    public CloseAllGrippers(Gripper gripper) {
        m_gripper = gripper;
//        requires(topGripper);
//        requires(middleGripper);
        requires(m_gripper);
    }

    //Command for closing the bottom gripper around the pyramid


    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
//        topGripper.closeGrip();
//        middleGripper.closeGrip();
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
