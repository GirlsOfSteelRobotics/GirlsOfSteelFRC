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
public class RaiseBlocker extends CommandBase{

    private final Feeder m_feeder;

    public RaiseBlocker(Feeder feeder){
        m_feeder = feeder;
        requires(m_feeder);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        m_feeder.pushBlocker();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        end();
    }

}
