package com.gos.recycle_rush.robot.commands.drive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import com.gos.recycle_rush.robot.subsystems.Chassis;

/**
 *
 */
public class DriveByJoystick extends Command {

    private final Chassis m_chassis;
    private final Joystick m_chassisJoystick;

    public DriveByJoystick(Joystick joystick, Chassis chassis) {
        m_chassis = chassis;
        m_chassisJoystick = joystick;
        addRequirements(m_chassis);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_chassis.printPositionsToSmartDashboard();
        m_chassis.moveByJoystick(m_chassisJoystick);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
    }


}
