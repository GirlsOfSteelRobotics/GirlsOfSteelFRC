package com.gos.steam_works.commands;

import com.gos.steam_works.OI;
import com.gos.steam_works.subsystems.Chassis;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 *
 */
public class SwitchToDriveBackward extends CommandBase {

    private final Chassis m_chassis;
    private final OI m_oi;

    public SwitchToDriveBackward(Chassis chassis, OI oi) {
        m_chassis = chassis;
        m_oi = oi;
        addRequirements(m_chassis);
    }


    @Override
    public void initialize() {

        m_oi.setDriveDirection(OI.DriveDirection.kREV);
    }


    @Override
    public void execute() {
    }


    @Override
    public boolean isFinished() {
        return true;
    }


    @Override
    public void end(boolean interrupted) {
    }


}
