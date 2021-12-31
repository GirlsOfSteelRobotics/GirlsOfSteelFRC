/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.ultimate_ascent.commands;

import com.gos.ultimate_ascent.subsystems.Gripper;

/**
 * @author sam
 */
public class OpenGripAtBar extends CommandBase {
    private final Gripper m_gripper;

    public OpenGripAtBar(Gripper gripper) {
        requires(gripper);
        this.m_gripper = gripper;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return m_gripper.atBar();
    }

    @Override
    protected void end() {
        m_gripper.openGrip();
    }

    @Override
    protected void interrupted() {
    }
}
