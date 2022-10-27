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
        //        addRequirements(topGripper);
        //        addRequirements(middleGripper);
        addRequirements(m_gripper);
    }

    //Command for closing the bottom gripper around the pyramid


    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        //        topGripper.closeGrip();
        //        middleGripper.closeGrip();
        m_gripper.closeGrip();
    }

    @Override
    public boolean isFinished() {
        return true;

    }

    @Override
    public void end(boolean interrupted) {
    }



}
