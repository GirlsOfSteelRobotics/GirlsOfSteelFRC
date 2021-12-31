package com.gos.stronghold.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.gos.stronghold.robot.subsystems.Chassis;
import com.gos.stronghold.robot.subsystems.Flap;
import com.gos.stronghold.robot.subsystems.Pivot;

/**
 *
 */
public class ResetEncoderDistance extends Command {

    private final Chassis m_chassis;
    private final Flap m_flap;
    private final Pivot m_pivot;

    public ResetEncoderDistance(Chassis chassis, Flap flap, Pivot pivot) {
        m_chassis = chassis;
        m_flap = flap;
        m_pivot = pivot;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(m_chassis);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        m_chassis.resetEncoderDistance();
        m_flap.resetDistance();
        m_pivot.resetDistance();
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
    }
}
