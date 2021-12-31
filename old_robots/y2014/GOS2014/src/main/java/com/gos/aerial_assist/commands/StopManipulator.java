/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import com.gos.aerial_assist.subsystems.Manipulator;

/**
 * @author appasamysm
 */
//Is this nessisary?
public class StopManipulator extends CommandBase {

    private final Manipulator m_manipulator;

    public StopManipulator(Manipulator manipulator) {
        m_manipulator = manipulator;
        requires(m_manipulator);
    }

    @Override
    protected void initialize() {
        m_manipulator.stopManipulator();
    }

    @Override
    protected void execute() {
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
