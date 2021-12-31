package com.gos.stronghold.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.gos.stronghold.robot.subsystems.Chassis;

/**
 *
 */
public class Drive extends Command {
    private final Chassis m_chassis;
    private final double m_move;
    private final double m_angle;

    public Drive(Chassis chassis, double distance, double rotate) {
        m_chassis = chassis;
        requires(m_chassis);
        m_move = distance;
        m_angle = rotate;

    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        m_chassis.drive(m_move, m_angle);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return true;
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
