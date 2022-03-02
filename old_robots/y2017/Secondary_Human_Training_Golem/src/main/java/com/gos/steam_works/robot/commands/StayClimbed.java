package com.gos.steam_works.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.steam_works.robot.subsystems.Climber;

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

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        // m_climber.climbMotorA.setPosition(0);
        m_encPosition = m_climber.getPosition();
        System.out.println("Climber Encoder Position: " + m_encPosition);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_climber.goToPosition(m_encPosition);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
    }


}
