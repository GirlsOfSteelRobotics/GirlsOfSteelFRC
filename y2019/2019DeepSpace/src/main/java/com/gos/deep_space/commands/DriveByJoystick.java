package com.gos.deep_space.commands;

import com.gos.deep_space.OI;
import com.gos.deep_space.subsystems.Chassis;
import edu.wpi.first.wpilibj.command.Command;

public class DriveByJoystick extends Command {

    private final Chassis m_chassis;
    private final OI m_oi;

    public DriveByJoystick(Chassis chassis, OI oi) {
        m_chassis = chassis;
        m_oi = oi;
        requires(m_chassis);
    }


    @Override
    protected void initialize() {

    }


    @Override
    protected void execute() {
        // 4 is the axis number right x on the gamepad
        m_chassis.driveByJoystick(m_oi.getLeftUpAndDown(), m_oi.getRightSideToSide());
    }


    @Override
    protected boolean isFinished() {
        return false;
    }


    @Override
    protected void end() {
        m_chassis.stop();
    }


}
