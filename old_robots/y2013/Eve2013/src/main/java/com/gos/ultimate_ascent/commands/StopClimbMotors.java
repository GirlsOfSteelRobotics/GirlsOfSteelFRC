/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.ultimate_ascent.commands;

import com.gos.ultimate_ascent.subsystems.Climber;

/**
 * @author sam
 */
public class StopClimbMotors extends GosCommandBaseBase {

    private final Climber m_climber;

    public StopClimbMotors(Climber climber) {
        m_climber = climber;
        addRequirements(m_climber);
    }

    //stops the motors used for climbing


    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_climber.stopLeftClimberSpike();
        m_climber.stopRightClimberSpike();
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {
    }



}
