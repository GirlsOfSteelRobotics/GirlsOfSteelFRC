package com.gos.steam_works.commands;

import com.gos.steam_works.OI;
import com.gos.steam_works.subsystems.Chassis;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SwitchToDriveForward extends Command {

    private final Chassis m_chassis;
    private final OI m_oi;

    public SwitchToDriveForward(Chassis chassis, OI oi) {
        m_chassis = chassis;
        m_oi = oi;
        requires(m_chassis);
    }


    @Override
    protected void initialize() {

        m_oi.setDriveDirection(OI.DriveDirection.kFWD);
    }


    @Override
    protected void execute() {
    }


    @Override
    protected boolean isFinished() {
        return true;
    }


    @Override
    protected void end() {
    }


}
