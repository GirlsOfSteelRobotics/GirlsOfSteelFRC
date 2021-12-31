/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package girlsofsteel.commands;

import girlsofsteel.subsystems.Climber;

/**
 * @author sam
 */
public class RetractClimberPiston extends CommandBase {

    private final Climber m_climber;

    public RetractClimberPiston(Climber climber) {
        m_climber = climber;
        requires(m_climber);
    }


    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        m_climber.retractLifterPiston();
    }

    @Override
    protected boolean isFinished() {
        return !m_climber.isPistonExtended();
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }

}
