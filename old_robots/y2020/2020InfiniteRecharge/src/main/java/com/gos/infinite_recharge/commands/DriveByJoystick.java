package com.gos.infinite_recharge.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.infinite_recharge.subsystems.Chassis;

public class DriveByJoystick extends CommandBase {

    private final Chassis m_chassis;
    public XboxController m_drivingPad;

    public DriveByJoystick(Chassis chassis, XboxController drivingPad) {
        this.m_chassis = chassis;
        this.m_drivingPad = drivingPad;

        super.addRequirements(chassis);
    }

    @Override
    public void execute() {
        m_chassis.setSpeedAndSteer(-m_drivingPad.getLeftY(), m_drivingPad.getRightX());
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
