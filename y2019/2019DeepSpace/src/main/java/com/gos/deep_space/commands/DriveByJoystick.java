package com.gos.deep_space.commands;

import com.gos.deep_space.OI;
import com.gos.deep_space.subsystems.Chassis;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveByJoystick extends CommandBase {

    private final Chassis m_chassis;
    private final OI m_oi;

    public DriveByJoystick(Chassis chassis, OI oi) {
        m_chassis = chassis;
        m_oi = oi;
        addRequirements(m_chassis);
    }


    @Override
    public void initialize() {

    }


    @Override
    public void execute() {
        // 4 is the axis number right x on the gamepad
        m_chassis.driveByJoystick(m_oi.getLeftUpAndDown(), m_oi.getRightSideToSide());
    }


    @Override
    public boolean isFinished() {
        return false;
    }


    @Override
    public void end(boolean interrupted) {
        m_chassis.stop();
    }


}
