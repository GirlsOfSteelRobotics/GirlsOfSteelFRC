package com.gos.infinite_recharge.commands;

import com.gos.infinite_recharge.OI;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.infinite_recharge.subsystems.Chassis;

public class DriveByJoystick extends CommandBase {

    private final Chassis m_chassis;
    private final OI m_oi;

    public DriveByJoystick(Chassis chassis, OI oi) {
        this.m_chassis = chassis;
        this.m_oi = oi;

        super.addRequirements(chassis);
    }

    @Override
    public void execute() {
        m_chassis.setSpeedAndSteer(m_oi.getJoystickSpeed(), m_oi.getJoystickSpin());
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
