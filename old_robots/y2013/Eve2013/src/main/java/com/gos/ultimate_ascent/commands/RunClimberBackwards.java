/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.ultimate_ascent.commands;

import com.gos.ultimate_ascent.subsystems.Climber;

/**
 * @author sam
 */
public class RunClimberBackwards extends GosCommandBase {

    private final Climber m_climber;

    public RunClimberBackwards(Climber climber) {
        m_climber = climber;
        addRequirements(m_climber);
    }

    //Command for starting the motors to begin climbing


    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_climber.reverseLeftClimberSpike();
        m_climber.reverseRightClimberSpike();
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
