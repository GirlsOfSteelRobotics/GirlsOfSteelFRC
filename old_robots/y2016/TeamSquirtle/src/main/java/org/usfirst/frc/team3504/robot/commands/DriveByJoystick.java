package org.usfirst.frc.team3504.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3504.robot.OI;
import org.usfirst.frc.team3504.robot.subsystems.Chassis;

/**
 *
 */
public class DriveByJoystick extends Command {

    private final Joystick m_stick;
    private final Chassis m_chassis;

    public DriveByJoystick(OI oi, Chassis chassis) {
        m_stick = oi.getJoystick();
        m_chassis = chassis;
        requires(m_chassis);

    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        m_chassis.driveByJoystick(m_stick);
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
    }
}
