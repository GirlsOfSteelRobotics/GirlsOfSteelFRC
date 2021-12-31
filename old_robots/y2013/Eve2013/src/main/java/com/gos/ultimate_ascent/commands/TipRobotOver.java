/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.ultimate_ascent.commands;

import com.gos.ultimate_ascent.subsystems.Climber;

/**
 * @author sam
 */
public class TipRobotOver extends CommandBase {


    private final Climber m_climber;

    public TipRobotOver(Climber climber) {
        m_climber = climber;
        requires(m_climber);
    }

    //will extend the piston to tip the robot over onto the pyramid


    @Override
    protected void initialize() {


    }

    @Override
    protected void execute() {
        m_climber.extendLifterPiston();
    }

    @Override
    protected boolean isFinished() {
        return m_climber.isPistonExtended();
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {
    }

}
