package com.gos.steam_works.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.steam_works.robot.OI;
import com.gos.steam_works.robot.OI.DriveDirection;
import com.gos.steam_works.robot.subsystems.Chassis;

/**
 *
 */
public class SwitchToDriveBackward extends CommandBase {

    private final Chassis m_chassis;
    private final OI m_oi;

    public SwitchToDriveBackward(OI oi, Chassis chassis) {
        m_oi = oi;
        m_chassis = chassis;
        addRequirements(m_chassis);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_oi.setDriveDirection(DriveDirection.kREV);
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
