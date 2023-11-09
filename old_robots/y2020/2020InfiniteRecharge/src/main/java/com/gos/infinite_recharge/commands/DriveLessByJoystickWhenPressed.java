package com.gos.infinite_recharge.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import com.gos.infinite_recharge.subsystems.Chassis;

public class DriveLessByJoystickWhenPressed extends Command {
    private final Chassis m_chassis;
    public XboxController m_drivingPad;
    private final double m_speedReduction;

    public DriveLessByJoystickWhenPressed(Chassis chassis, XboxController drivingPad) {
        this.m_chassis = chassis;
        this.m_drivingPad = drivingPad;
        super.addRequirements(chassis);

        m_speedReduction = 0.5;
    }

    @Override
    public void execute() {
        m_chassis.setSpeedAndSteer(-m_drivingPad.getLeftY() * m_speedReduction, m_drivingPad.getRightX() * m_speedReduction);
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
