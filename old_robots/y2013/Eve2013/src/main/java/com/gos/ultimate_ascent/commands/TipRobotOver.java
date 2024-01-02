/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.ultimate_ascent.commands;

import com.gos.ultimate_ascent.subsystems.Climber;

/**
 * @author sam
 */
public class TipRobotOver extends GosCommand {


    private final Climber m_climber;

    public TipRobotOver(Climber climber) {
        m_climber = climber;
        addRequirements(m_climber);
    }

    //will extend the piston to tip the robot over onto the pyramid


    @Override
    public void initialize() {


    }

    @Override
    public void execute() {
        m_climber.extendLifterPiston();
    }

    @Override
    public boolean isFinished() {
        return m_climber.isPistonExtended();
    }

    @Override
    public void end(boolean interrupted) {

    }



}
