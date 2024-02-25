/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.ultimate_ascent.commands;

import com.gos.ultimate_ascent.subsystems.Gripper;

/**
 * @author sam
 */
public class CloseBottomGrip extends GosCommandBaseBase {

    private final Gripper m_gripper;

    public CloseBottomGrip(Gripper gripper) {
        m_gripper = gripper;
        addRequirements(m_gripper);
    }

    //Command for closing the bottom gripper around the pyramid


    @Override
    public void initialize() {

    }

    @Override
    public void execute() {

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
