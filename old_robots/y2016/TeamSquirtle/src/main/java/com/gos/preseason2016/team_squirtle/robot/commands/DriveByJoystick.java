package com.gos.preseason2016.team_squirtle.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import com.gos.preseason2016.team_squirtle.robot.OI;
import com.gos.preseason2016.team_squirtle.robot.subsystems.Chassis;

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
