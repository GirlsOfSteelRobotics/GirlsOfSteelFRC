/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import com.gos.aerial_assist.subsystems.Kicker;

/**
 * @author Sylvie
 * <p>
 * Stops the kicker wherever it is (does not stop the PID)
 */
public class StopKicker extends CommandBase {

    private final Kicker m_kicker;

    public StopKicker(Kicker kicker) {
        m_kicker = kicker;
        addRequirements(m_kicker);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return false; //this never ends.
    }

    @Override
    public void end(boolean interrupted) {
    }



}
