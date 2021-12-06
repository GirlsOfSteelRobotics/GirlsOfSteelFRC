package org.usfirst.frc.team3504.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3504.robot.subsystems.Collector;
import org.usfirst.frc.team3504.robot.subsystems.JawPiston;

/**
 *
 */
public class Collect extends Command {

    private final JawPiston m_jaw;
    private final Collector m_collector;

    public Collect(JawPiston jaw, Collector collector) {
        m_jaw = jaw;
        m_collector = collector;
        requires(m_jaw);
        requires(m_collector);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        //Robot.arm.armDown();
        m_jaw.pistonsIn();
        m_collector.spinWheels(0.5); //TODO : test
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        m_collector.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
