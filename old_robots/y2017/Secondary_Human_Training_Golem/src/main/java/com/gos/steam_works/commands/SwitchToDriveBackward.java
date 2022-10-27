package com.gos.steam_works.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.steam_works.subsystems.Chassis;

/**
 *
 */
public class SwitchToDriveBackward extends CommandBase {

    private final Chassis m_chassis;

    public SwitchToDriveBackward(Chassis chassis) {
        m_chassis = chassis;
        addRequirements(m_chassis);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_chassis.setTeleDriveDirection(Chassis.TeleDriveDirection.REV);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
    }


}
