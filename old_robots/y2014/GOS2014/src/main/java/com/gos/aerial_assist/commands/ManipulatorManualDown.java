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
public class ManipulatorManualDown extends GosCommandBaseBase {

    private final Manipulator m_manipulator;

    public ManipulatorManualDown(Manipulator manipulator) {
        m_manipulator = manipulator;
        addRequirements(m_manipulator);
    }

    @Override
    public void initialize() {
        m_manipulator.disablePID();
    }

    @Override
    public void execute() {
        m_manipulator.moveManipulatorDown();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_manipulator.stopManipulator();
        //manipulator.startPID();
        //manipulator.resetPIDError();
        m_manipulator.initEncoder();
    }


}
