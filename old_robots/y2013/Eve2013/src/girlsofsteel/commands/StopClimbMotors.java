/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

import girlsofsteel.subsystems.Climber;

/**
 *
 * @author sam
 */
public class StopClimbMotors extends CommandBase {

    private final Climber m_climber;

    public StopClimbMotors(Climber climber) {
        m_climber = climber;
          requires (m_climber);
    }

    //stops the motors used for climbing


    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        m_climber.stopLeftClimberSpike();
        m_climber.stopRightClimberSpike();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }

}
