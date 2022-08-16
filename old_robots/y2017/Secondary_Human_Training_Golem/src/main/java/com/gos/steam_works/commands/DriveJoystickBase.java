package com.gos.steam_works.commands;

import com.gos.steam_works.subsystems.Chassis;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 *
 */
public abstract class DriveJoystickBase extends CommandBase {

    protected final Chassis m_chassis;

    public DriveJoystickBase(Chassis chassis) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        m_chassis = chassis;
        addRequirements(m_chassis);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    public void stop() {
        m_chassis.stop();
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        stop();
    }
}
