/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.ultimate_ascent.commands;

import com.gos.ultimate_ascent.subsystems.Gripper;

/**
 * @author sam
 */
public class OpenGripAtBar extends GosCommand {
    private final Gripper m_gripper;

    public OpenGripAtBar(Gripper gripper) {
        addRequirements(gripper);
        this.m_gripper = gripper;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return m_gripper.atBar();
    }

    @Override
    public void end(boolean interrupted) {
        m_gripper.openGrip();
    }


}
