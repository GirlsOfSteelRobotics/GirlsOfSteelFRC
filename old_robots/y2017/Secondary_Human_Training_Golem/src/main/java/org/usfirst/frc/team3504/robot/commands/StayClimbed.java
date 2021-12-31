package org.usfirst.frc.team3504.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3504.robot.subsystems.Climber;

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

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        // m_climber.climbMotorA.setPosition(0);
        m_encPosition = m_climber.getPosition();
        System.out.println("Climber Encoder Position: " + m_encPosition);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        m_climber.goToPosition(m_encPosition);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
