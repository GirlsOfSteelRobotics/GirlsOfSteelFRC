/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package girlsofsteel.commands;

import girlsofsteel.subsystems.Feeder;

/**
 *
 * @author GirlsOfSTEEL
 */
public class Blocking extends CommandBase{
    private final Feeder m_feeder;
    private boolean m_blockerRaised;
    private boolean m_newBlockerRaised;
    private boolean m_oldBlockerRaised;

        public Blocking(Feeder feeder){
            m_feeder = feeder;
            m_blockerRaised = false;
            requires (m_feeder);
        }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
            m_blockerRaised = m_feeder.getIsRaised();
            m_oldBlockerRaised = m_blockerRaised;
            if(!m_blockerRaised){
                m_feeder.pushBlocker();
                m_feeder.setIsRaised(true);
            }
            else{
                m_feeder.pullBlocker();
                m_feeder.setIsRaised(false);
            }
            m_newBlockerRaised = m_feeder.getIsRaised();
    }

    @Override
    protected boolean isFinished() {
        return m_oldBlockerRaised != m_newBlockerRaised;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        end();
    }
}
