/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.ultimate_ascent.commands;

import com.gos.ultimate_ascent.subsystems.Gripper;

/**
 * @author sam
 */

//this command reads the limit switches under the grippers. When press the grippers will close after safely passing the bar
public class CloseGripPastBar extends GosCommandBaseBase {
    private final Gripper m_gripper;

    public CloseGripPastBar(Gripper gripper) {
        this.m_gripper = gripper;

        addRequirements(gripper);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return m_gripper.pastBar();
    }

    @Override
    public void end(boolean interrupted) {
        m_gripper.closeGrip();
    }



}
