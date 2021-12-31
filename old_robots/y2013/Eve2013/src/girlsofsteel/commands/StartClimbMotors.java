/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package girlsofsteel.commands;

import girlsofsteel.subsystems.Climber;

/**
 * @author sam
 */
public class StartClimbMotors extends CommandBase {

    private final Climber m_climber;

    public StartClimbMotors(Climber climber) {
        m_climber = climber;
        requires(m_climber);
    }

    //Command for starting the motors to begin climbing


    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        m_climber.forwardLeftClimberSpike();
        m_climber.forwardRightClimberSpike();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        m_climber.stopLeftClimberSpike();
        m_climber.stopRightClimberSpike();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
