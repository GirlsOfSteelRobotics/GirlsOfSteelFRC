package com.gos.infinite_recharge.commands;

import com.gos.infinite_recharge.OI;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.infinite_recharge.subsystems.Chassis;

public class DriveLessByJoystickWhenPressed extends CommandBase {
    private final Chassis m_chassis;
    private final OI m_oi;
    private final double m_speedReduction;

    public DriveLessByJoystickWhenPressed(Chassis chassis, OI oi) {
        this.m_chassis = chassis;
        this.m_oi = oi;
        super.addRequirements(chassis);

        m_speedReduction = 0.5;
    }

    @Override
    public void execute() {
        System.out.println(m_chassis + " , " + m_oi);
        m_chassis.setSpeedAndSteer(m_oi.getJoystickSpeed() * m_speedReduction, m_oi.getJoystickSpin() * m_speedReduction);
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
