package org.usfirst.frc.team3504.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3504.robot.subsystems.Chassis;

/**
 *
 */
public class AutoTurnCounterClockwise extends Command {

    private final Chassis m_chassis;
    private double m_gyroInitial;

    public AutoTurnCounterClockwise(Chassis chassis) {
        m_chassis = chassis;
        requires(m_chassis);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        m_gyroInitial = m_chassis.getGyroAngle();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        m_chassis.autoTurnCounterclockwise();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return (m_gyroInitial - m_chassis.getGyroAngle()) >= 90;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        m_chassis.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
