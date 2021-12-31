/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import com.gos.aerial_assist.subsystems.Manipulator;

/**
 * @author Abby
 */
public class ManipulatorManualDown extends CommandBase {

    private final Manipulator m_manipulator;

    public ManipulatorManualDown(Manipulator manipulator) {
        m_manipulator = manipulator;
        requires(m_manipulator);
    }

    @Override
    protected void initialize() {
        m_manipulator.disablePID();
    }

    @Override
    protected void execute() {
        m_manipulator.moveManipulatorDown();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        m_manipulator.stopManipulator();
        //manipulator.startPID();
        //manipulator.resetPIDError();
        m_manipulator.initEncoder();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
