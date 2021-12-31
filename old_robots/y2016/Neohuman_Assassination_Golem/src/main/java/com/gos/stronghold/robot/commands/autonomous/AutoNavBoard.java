package com.gos.stronghold.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.Command;
import com.gos.stronghold.robot.subsystems.Chassis;

/**
 *
 */
public class AutoNavBoard extends Command {

    private final Chassis m_chassis;
    private final double m_inches;

    public AutoNavBoard(Chassis chassis, double distance) {
        m_chassis = chassis;
        requires(m_chassis);
        m_inches = distance;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        m_chassis.resetEncoderDistance();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        m_chassis.driveSpeed(-.4);
        m_chassis.printEncoderValues();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return m_chassis.getEncoderDistance() >= m_inches;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
