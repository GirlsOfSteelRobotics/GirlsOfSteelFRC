package org.usfirst.frc.team3504.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3504.robot.OI;
import org.usfirst.frc.team3504.robot.OI.DriveDirection;
import org.usfirst.frc.team3504.robot.subsystems.Chassis;

/**
 *
 */
public class SwitchToDriveForward extends Command {

    private final OI m_oi;
    private final Chassis m_chassis;

    public SwitchToDriveForward(OI oi, Chassis chassis) {
        m_oi = oi;
        m_chassis = chassis;
        requires(m_chassis);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        m_chassis.setPercentVbusMode();

        m_oi.setDriveDirection(DriveDirection.kFWD);
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
