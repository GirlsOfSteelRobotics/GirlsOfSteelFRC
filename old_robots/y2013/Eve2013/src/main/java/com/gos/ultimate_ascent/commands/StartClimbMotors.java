/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.ultimate_ascent.commands;

import com.gos.ultimate_ascent.subsystems.Climber;

/**
 * @author sam
 */
public class StartClimbMotors extends CommandBase {

    private final Climber m_climber;

    public StartClimbMotors(Climber climber) {
        m_climber = climber;
        addRequirements(m_climber);
    }

    //Command for starting the motors to begin climbing


    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_climber.forwardLeftClimberSpike();
        m_climber.forwardRightClimberSpike();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_climber.stopLeftClimberSpike();
        m_climber.stopRightClimberSpike();
    }



}
