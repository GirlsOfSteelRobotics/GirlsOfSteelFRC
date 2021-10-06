package com.gos.steam_works.commands;

import com.gos.steam_works.subsystems.Climber;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StayClimbed extends Command {

    private final Climber m_climber;
    private double m_encPosition;

    public StayClimbed(Climber climber) {
        m_climber = climber;
        requires(m_climber);
    }


    @Override
    protected void initialize() {

        // Robot.climber.climbMotorA.setPosition(0);
        m_encPosition = m_climber.getPosition();
        System.out.println("Climber Encoder Position: " + m_encPosition);
    }


    @Override
    protected void execute() {
        m_climber.goToPosition(m_encPosition);
    }


    @Override
    protected boolean isFinished() {
        return false;
    }


    @Override
    protected void end() {
    }


}
