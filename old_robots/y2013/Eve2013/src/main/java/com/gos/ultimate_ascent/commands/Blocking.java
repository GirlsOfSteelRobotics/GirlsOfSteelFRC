/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.ultimate_ascent.commands;

import com.gos.ultimate_ascent.subsystems.Feeder;

/**
 * @author GirlsOfSTEEL
 */
public class Blocking extends GosCommandBase {
    private final Feeder m_feeder;
    private boolean m_blockerRaised;
    private boolean m_newBlockerRaised;
    private boolean m_oldBlockerRaised;

    public Blocking(Feeder feeder) {
        m_feeder = feeder;
        m_blockerRaised = false;
        addRequirements(m_feeder);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_blockerRaised = m_feeder.getIsRaised();
        m_oldBlockerRaised = m_blockerRaised;
        if (!m_blockerRaised) {
            m_feeder.pushBlocker();
            m_feeder.setIsRaised(true);
        } else {
            m_feeder.pullBlocker();
            m_feeder.setIsRaised(false);
        }
        m_newBlockerRaised = m_feeder.getIsRaised();
    }

    @Override
    public boolean isFinished() {
        return m_oldBlockerRaised != m_newBlockerRaised;
    }

    @Override
    public void end(boolean interrupted) {
    }


}
