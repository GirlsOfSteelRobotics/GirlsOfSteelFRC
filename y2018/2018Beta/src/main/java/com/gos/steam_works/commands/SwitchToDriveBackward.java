package com.gos.steam_works.commands;

import com.gos.steam_works.OI;
import com.gos.steam_works.subsystems.Chassis;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SwitchToDriveBackward extends Command {

    private final Chassis m_chassis;
    private final OI m_oi;

    public SwitchToDriveBackward(Chassis chassis, OI oi) {
        m_chassis = chassis;
        m_oi = oi;
        requires(m_chassis);
    }


    @Override
    protected void initialize() {

        m_oi.setDriveDirection(OI.DriveDirection.kREV);
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
