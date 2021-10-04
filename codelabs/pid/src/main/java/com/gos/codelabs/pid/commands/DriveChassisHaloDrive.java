package com.gos.codelabs.pid.commands;

import com.gos.codelabs.pid.OI;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.codelabs.pid.subsystems.ChassisSubsystem;

public class DriveChassisHaloDrive extends CommandBase {
    private final ChassisSubsystem m_chassis;
    private final OI m_oi;

    public DriveChassisHaloDrive(ChassisSubsystem chassis, OI oi) {
        m_chassis = chassis;
        m_oi = oi;

        addRequirements(chassis);
    }

    @Override
    public void execute() {
        m_chassis.setSpeedAndSteer(m_oi.getDriverThrottle(), m_oi.getDriverSpin());
    }
}
