/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

import girlsofsteel.subsystems.Kicker;

/**
 *
 * @author Sylvie
 *
 * Stops the kicker wherever it is (does not stop the PID)
 */
public class StopKicker extends CommandBase {

    private final Kicker m_kicker;

    public StopKicker(Kicker kicker) {
        m_kicker = kicker;
        requires(m_kicker);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return false; //this never ends.
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        end();
    }

}
