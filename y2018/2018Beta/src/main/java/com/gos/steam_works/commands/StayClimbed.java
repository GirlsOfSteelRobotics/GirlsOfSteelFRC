package com.gos.steam_works.commands;

import com.gos.steam_works.subsystems.Climber;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 *
 */
public class StayClimbed extends CommandBase {

    private final Climber m_climber;
    private double m_encPosition;

    public StayClimbed(Climber climber) {
        m_climber = climber;
        addRequirements(m_climber);
    }


    @Override
    public void initialize() {

        // Robot.climber.climbMotorA.setPosition(0);
        m_encPosition = m_climber.getPosition();
        System.out.println("Climber Encoder Position: " + m_encPosition);
    }


    @Override
    public void execute() {
        m_climber.goToPosition(m_encPosition);
    }


    @Override
    public boolean isFinished() {
        return false;
    }


    @Override
    public void end(boolean interrupted) {
    }


}
