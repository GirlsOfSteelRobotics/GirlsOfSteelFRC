package com.gos.recycle_rush.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.recycle_rush.robot.subsystems.Lifter;

/**
 *
 */
public class AutoLift extends Command {

    private final Lifter m_lifter;
    private final double m_distance;

    public AutoLift(Lifter lifter, double distance) {
        m_lifter = lifter;
        addRequirements(m_lifter);
        this.m_distance = distance;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_lifter.setPosition(m_distance);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return m_lifter.isAtPosition();
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_lifter.stop();
    }


}
