/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.ultimate_ascent.commands;

import com.gos.ultimate_ascent.subsystems.Gripper;

/**
 * @author sam
 */
public class OpenAllGrippers extends GosCommandBase {

    private final Gripper m_gripper;

    public OpenAllGrippers(Gripper bottomGripper) {
        //        addRequirements(topGripper);
        //        addRequirements(middleGripper);
        m_gripper = bottomGripper;
        addRequirements(m_gripper);
    }

    //Command for closing the bottom gripper around the pyramid


    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        //        topGripper.openGrip();
        //        middleGripper.openGrip();
        m_gripper.openGrip();
    }

    @Override
    public boolean isFinished() {
        return true;

    }

    @Override
    public void end(boolean interrupted) {
    }



}
