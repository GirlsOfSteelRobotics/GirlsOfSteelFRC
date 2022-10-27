/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.ultimate_ascent.commands;

import com.gos.ultimate_ascent.subsystems.Feeder;

/**
 * @author GirlsOfSTEEL
 */
public class RaiseBlocker extends CommandBase {

    private final Feeder m_feeder;

    public RaiseBlocker(Feeder feeder) {
        m_feeder = feeder;
        addRequirements(m_feeder);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_feeder.pushBlocker();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
    }



}
