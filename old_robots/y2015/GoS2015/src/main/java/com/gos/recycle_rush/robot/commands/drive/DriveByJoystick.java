package com.gos.recycle_rush.robot.commands.drive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import com.gos.recycle_rush.robot.OI;
import com.gos.recycle_rush.robot.subsystems.Chassis;

/**
 *
 */
public class DriveByJoystick extends Command {

    private final Chassis m_chassis;
    private final Joystick m_chassisJoystick;

    public DriveByJoystick(OI oi, Chassis chassis) {
        m_chassis = chassis;
        m_chassisJoystick = oi.getChassisJoystick();
        requires(m_chassis);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        m_chassis.printPositionsToSmartDashboard();
        m_chassis.moveByJoystick(m_chassisJoystick);
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
