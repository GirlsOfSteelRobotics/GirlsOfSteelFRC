/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.ultimate_ascent.commands;

import com.gos.ultimate_ascent.subsystems.Gripper;

/**
 * @author sam
 */
public class OpenAllGrippers extends CommandBase {

    private final Gripper m_gripper;

    public OpenAllGrippers(Gripper bottomGripper) {
//        requires(topGripper);
//        requires(middleGripper);
        m_gripper = bottomGripper;
        requires(m_gripper);
    }

    //Command for closing the bottom gripper around the pyramid


    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
//        topGripper.openGrip();
//        middleGripper.openGrip();
        m_gripper.openGrip();
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
