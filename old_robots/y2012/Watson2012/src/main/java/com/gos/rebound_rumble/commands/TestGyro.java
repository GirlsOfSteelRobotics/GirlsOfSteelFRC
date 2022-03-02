package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.rebound_rumble.subsystems.Chassis;
import com.gos.rebound_rumble.subsystems.Turret;

public class TestGyro extends CommandBase {
    private final Chassis m_chassis;
    private final Turret m_turret;

    public TestGyro(Chassis chassis, Turret turret) {
        m_chassis = chassis;
        m_turret = turret;
        addRequirements(m_chassis);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        SmartDashboard.putNumber("Gyro Angle", m_chassis.getTheta());
        SmartDashboard.putNumber("Turret Encoder Angle", m_turret.getEncoderDistance());
        SmartDashboard.putNumber("Turret Angle (Summation)", m_turret.getTurretAngle());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
    }



}
